package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R

class FifthFragment : Fragment() {

    private lateinit var emailField: EditText
    private var savedEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fifth, container, false)

        emailField = view.findViewById(R.id.etEmailAddressThirdActivity)

        // Restore the saved email if available
        savedInstanceState?.let {
            savedEmail = it.getString("email")
            emailField.setText(savedEmail)
        }

        val btnBack: Button = view.findViewById(R.id.btnBack)
        val btnConfirm: Button = view.findViewById(R.id.btnConfirmMail)

        btnConfirm.setOnClickListener {
            val emailText = emailField.text.toString().trim()

            if (validateFields(emailField)) {
                if (isValidEmail(emailText)) {
                    findNavController().navigate(R.id.action_fifthFragment_to_resetPass)
                } else {
                    emailField.error = "Enter a valid email"
                    emailField.requestFocus()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    private fun validateFields(vararg fields: EditText): Boolean {
        var allValid = true
        for (field in fields) {
            if (TextUtils.isEmpty(field.text.toString().trim())) {
                field.error = "This field cannot be empty"
                allValid = false
            }
        }
        return allValid
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the email text
        outState.putString("email", emailField.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the email field value
        savedInstanceState?.let {
            val savedEmail = it.getString("email")
            emailField.setText(savedEmail)
        }
    }
}


