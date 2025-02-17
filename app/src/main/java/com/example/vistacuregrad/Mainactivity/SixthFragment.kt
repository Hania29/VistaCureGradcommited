package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.Viewmodel.OtpViewModel
import com.example.vistacuregrad.Viewmodel.OtpViewModelFactory
import com.example.vistacuregrad.network.RetrofitClient

class SixthFragment : Fragment() {

    private lateinit var otpDigit1: EditText
    private lateinit var otpDigit2: EditText
    private lateinit var otpDigit3: EditText
    private lateinit var otpDigit4: EditText
    private lateinit var otpDigit5: EditText
    private lateinit var otpDigit6: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var btnOTP: Button

    private val otpViewModel: OtpViewModel by viewModels {
        OtpViewModelFactory(requireActivity().application, AuthRepository(RetrofitClient.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sixth, container, false)

        otpDigit1 = view.findViewById(R.id.otpDigit1)
        otpDigit2 = view.findViewById(R.id.otpDigit2)
        otpDigit3 = view.findViewById(R.id.otpDigit3)
        otpDigit4 = view.findViewById(R.id.otpDigit4)
        otpDigit5 = view.findViewById(R.id.otpDigit5)
        otpDigit6 = view.findViewById(R.id.otpDigit6)
        progressBar = view.findViewById(R.id.progressBar)
        btnOTP = view.findViewById(R.id.btnOTP)

        val btnBack = view.findViewById<Button>(R.id.btnBack)

        otpDigit1.addTextChangedListener(createOtpTextWatcher(otpDigit2))
        otpDigit2.addTextChangedListener(createOtpTextWatcher(otpDigit3, otpDigit1))
        otpDigit3.addTextChangedListener(createOtpTextWatcher(otpDigit4, otpDigit2))
        otpDigit4.addTextChangedListener(createOtpTextWatcher(otpDigit5, otpDigit3))
        otpDigit5.addTextChangedListener(createOtpTextWatcher(otpDigit6, otpDigit4))
        otpDigit6.addTextChangedListener(createOtpTextWatcher(null, otpDigit5))

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnOTP.setOnClickListener {
            val otpCode = getOtpCode()
            if (validateOtpFields(otpCode)) {
                btnOTP.isEnabled = false // Prevent multiple clicks
                verifyOtp(otpCode)
            }
        }

        observeOtpResponse()

        return view
    }

    private fun getOtpCode(): String {
        return otpDigit1.text.toString() +
                otpDigit2.text.toString() +
                otpDigit3.text.toString() +
                otpDigit4.text.toString() +
                otpDigit5.text.toString() +
                otpDigit6.text.toString()
    }

    private fun verifyOtp(otpCode: String) {
        progressBar.visibility = View.VISIBLE
        otpViewModel.verifyOtp(otpCode)
    }

    private fun observeOtpResponse() {
        otpViewModel.otpResponse.observe(viewLifecycleOwner, Observer { response ->
            progressBar.visibility = View.GONE
            btnOTP.isEnabled = true // Re-enable button

            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val otpResponse = it.body()
                    if (otpResponse?.status.equals("Success", ignoreCase = true)) {
                        Toast.makeText(requireContext(), "OTP Verified Successfully!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_sixthFragment_to_userProfile)
                    } else {
                        Toast.makeText(requireContext(), otpResponse?.message ?: "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    response.errorBody()?.string()?.let { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    } ?: Toast.makeText(requireContext(), "Failed to verify OTP. Try again.", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createOtpTextWatcher(next: EditText?, previous: EditText? = null): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && s.length == 1) {
                    next?.requestFocus()
                } else if (s.isNullOrEmpty() && before == 1) {
                    previous?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun validateOtpFields(otpCode: String): Boolean {
        if (otpCode.length != 6) {
            Toast.makeText(requireContext(), "Please enter a valid 6-digit OTP.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
