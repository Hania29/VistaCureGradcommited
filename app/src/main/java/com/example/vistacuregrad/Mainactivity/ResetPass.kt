package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R

class ResetPass : Fragment() {

    private var savedEmail: String? = null
    private var savedPassword: String? = null
    private var savedConfirmPassword: String? = null
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_pass, container, false)

        // Initialize EditText fields
        val etPass: EditText = view.findViewById(R.id.etPass)
        val etConfirmPass: EditText = view.findViewById(R.id.etConfirmPass)
        val etEmailAddress: EditText = view.findViewById(R.id.etemailaddress)

        // Initialize ImageViews for toggling password visibility
        val ivTogglePass: ImageView = view.findViewById(R.id.ivTogglePass)
        val ivToggleConfirmPass: ImageView = view.findViewById(R.id.ivToggleConfirmPass)

        // Restore saved data if available
        savedInstanceState?.let {
            savedEmail = it.getString("email")
            savedPassword = it.getString("password")
            savedConfirmPassword = it.getString("confirmPassword")

            etEmailAddress.setText(savedEmail)
            etPass.setText(savedPassword)
            etConfirmPass.setText(savedConfirmPassword)
        }

        // Set up password visibility toggle for "Password" field
        ivTogglePass.setOnClickListener {
            isPasswordVisible = togglePasswordVisibility(etPass, ivTogglePass, isPasswordVisible)
        }

        // Set up password visibility toggle for "Confirm Password" field
        ivToggleConfirmPass.setOnClickListener {
            isConfirmPasswordVisible = togglePasswordVisibility(etConfirmPass, ivToggleConfirmPass, isConfirmPasswordVisible)
        }

        // Initialize Done button
        val btnDone: Button = view.findViewById(R.id.btnDone)
        btnDone.setOnClickListener {
            if (validateFields(etPass, etConfirmPass, etEmailAddress)) {
                Toast.makeText(requireContext(), "Validation successful!", Toast.LENGTH_SHORT).show()
                // Proceed with next steps, e.g., navigating or sending data to a server
            }
        }

        // Initialize Back button
        val btnBack: Button = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView, isVisible: Boolean): Boolean {
        if (isVisible) {
            // Hide password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.hidden11) // Replace with your "hidden" icon
        } else {
            // Show password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.visabile) // Replace with your "visible" icon
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
        return !isVisible
    }

    private fun validateFields(etPass: EditText, etConfirmPass: EditText, etEmailAddress: EditText): Boolean {
        val password = etPass.text.toString()
        val confirmPassword = etConfirmPass.text.toString()
        val email = etEmailAddress.text.toString()

        // Email validation
        when {
            TextUtils.isEmpty(email) -> {
                etEmailAddress.error = "Email is required"
                etEmailAddress.requestFocus()
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmailAddress.error = "Enter a valid email"
                etEmailAddress.requestFocus()
                return false
            }
        }

        // Password validation
        when {
            TextUtils.isEmpty(password) -> {
                etPass.error = "Password is required"
                etPass.requestFocus()
                return false
            }
            password.length < 6 || password.length > 20 -> {
                etPass.error = "Password must be between 6 and 20 characters"
                etPass.requestFocus()
                return false
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                etPass.error = "Password must include at least one capital letter"
                etPass.requestFocus()
                return false
            }
            !password.matches(".*\\d.*".toRegex()) -> {
                etPass.error = "Password must include at least one number"
                etPass.requestFocus()
                return false
            }
            !password.matches(".*[@#\$%&!].*".toRegex()) -> {
                etPass.error = "Password must include at least one special character (@#\$%&!)"
                etPass.requestFocus()
                return false
            }
            password != confirmPassword -> {
                etConfirmPass.error = "Passwords do not match"
                etConfirmPass.requestFocus()
                return false
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the entered data when rotating
        outState.putString("email", savedEmail)
        outState.putString("password", savedPassword)
        outState.putString("confirmPassword", savedConfirmPassword)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the saved data (email, password, confirm password)
        savedInstanceState?.let {
            savedEmail = it.getString("email")
            savedPassword = it.getString("password")
            savedConfirmPassword = it.getString("confirmPassword")
        }
    }
}




