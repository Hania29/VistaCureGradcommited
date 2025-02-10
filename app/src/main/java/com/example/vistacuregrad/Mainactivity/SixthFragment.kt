package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R

class SixthFragment : Fragment() {

    private lateinit var otpDigit1: EditText
    private lateinit var otpDigit2: EditText
    private lateinit var otpDigit3: EditText
    private lateinit var otpDigit4: EditText
    private lateinit var otpDigit5: EditText
    private lateinit var otpDigit6: EditText

    private var otp1: String? = null
    private var otp2: String? = null
    private var otp3: String? = null
    private var otp4: String? = null
    private var otp5: String? = null
    private var otp6: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sixth, container, false)

        otpDigit1 = view.findViewById(R.id.otpDigit1)
        otpDigit2 = view.findViewById(R.id.otpDigit2)
        otpDigit3 = view.findViewById(R.id.otpDigit3)
        otpDigit4 = view.findViewById(R.id.otpDigit4)
        otpDigit5 = view.findViewById(R.id.otpDigit5)
        otpDigit6 = view.findViewById(R.id.otpDigit6)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        val btnOTP = view.findViewById<Button>(R.id.btnOTP)

        // Restore OTP data if available
        savedInstanceState?.let {
            otp1 = it.getString("otp1")
            otp2 = it.getString("otp2")
            otp3 = it.getString("otp3")
            otp4 = it.getString("otp4")
            otp5 = it.getString("otp5")
            otp6 = it.getString("otp6")

            otpDigit1.setText(otp1)
            otpDigit2.setText(otp2)
            otpDigit3.setText(otp3)
            otpDigit4.setText(otp4)
            otpDigit5.setText(otp5)
            otpDigit6.setText(otp6)
        }

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
            if (validateOtpFields()) {
                findNavController().navigate(R.id.action_sixthFragment_to_userProfile)
            }
        }

        return view
    }

    private fun createOtpTextWatcher(next: EditText?, previous: EditText? = null): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    next?.requestFocus()
                } else if (s.isNullOrEmpty()) {
                    previous?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun validateOtpFields(): Boolean {
        val otpFields = listOf(otpDigit1, otpDigit2, otpDigit3, otpDigit4, otpDigit5, otpDigit6)

        for ((index, otpField) in otpFields.withIndex()) {
            if (otpField.text.toString().isEmpty()) {
                otpField.error = "Field cannot be empty"
                otpField.requestFocus()
                return false
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the OTP digits when rotating
        outState.putString("otp1", otpDigit1.text.toString())
        outState.putString("otp2", otpDigit2.text.toString())
        outState.putString("otp3", otpDigit3.text.toString())
        outState.putString("otp4", otpDigit4.text.toString())
        outState.putString("otp5", otpDigit5.text.toString())
        outState.putString("otp6", otpDigit6.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the OTP digits after rotation
        savedInstanceState?.let {
            otp1 = it.getString("otp1")
            otp2 = it.getString("otp2")
            otp3 = it.getString("otp3")
            otp4 = it.getString("otp4")
            otp5 = it.getString("otp5")
            otp6 = it.getString("otp6")
        }
    }
}
