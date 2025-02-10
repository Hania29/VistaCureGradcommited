package com.example.vistacuregrad.Mainactivity

import android.content.Context
import android.content.SharedPreferences
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

class FourthFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var savedUserName: String? = null
    private var savedEmail: String? = null
    private var savedPassword: String? = null
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fourth, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)

        val btnLogIn = view.findViewById<Button>(R.id.btnLogInSecondActivity)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUpSecondActivity)

        val etUserName = view.findViewById<EditText>(R.id.etUserName)
        val etEmail = view.findViewById<EditText>(R.id.etEmailAddressSecondActivity)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val ivTogglePassword = view.findViewById<ImageView>(R.id.hiddenEye2)

        // Restore saved data if available
        savedInstanceState?.let {
            savedUserName = it.getString("username")
            savedEmail = it.getString("email")
            savedPassword = it.getString("password")
            etUserName.setText(savedUserName)
            etEmail.setText(savedEmail)
            etPassword.setText(savedPassword)
        }

        // Set up password visibility toggle
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = togglePasswordVisibility(etPassword, ivTogglePassword, isPasswordVisible)
        }

        btnSignUp.setOnClickListener {
            if (validateFields(etUserName, etEmail, etPassword)) {
                // Save the username and password to shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("username", etUserName.text.toString().trim())
                editor.putString("password", etPassword.text.toString().trim())
                editor.apply()

                findNavController().navigate(R.id.action_fourthFragment_to_seventhFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_fourthFragment_to_seventhFragment)
        }

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
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

    private fun validateFields(etUserName: EditText, etEmail: EditText, etPassword: EditText): Boolean {
        val userName = etUserName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Username validation
        if (TextUtils.isEmpty(userName)) {
            etUserName.error = "Username is required"
            etUserName.requestFocus()
            return false
        }

        // Email validation
        if (TextUtils.isEmpty(email)) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email address"
            etEmail.requestFocus()
            return false
        }

        // Password validation
        when {
            TextUtils.isEmpty(password) -> {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return false
            }
            password.length < 6 || password.length > 20 -> {
                etPassword.error = "Password must be between 6 and 20 characters"
                etPassword.requestFocus()
                return false
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                etPassword.error = "Password must include at least one capital letter"
                etPassword.requestFocus()
                return false
            }
            !password.matches(".*\\d.*".toRegex()) -> {
                etPassword.error = "Password must include at least one number"
                etPassword.requestFocus()
                return false
            }
            !password.matches(".*[@#\$%&!].*".toRegex()) -> {
                etPassword.error = "Password must include at least one special character (@#\$%&!)"
                etPassword.requestFocus()
                return false
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the username, email, and password when rotating
        outState.putString("username", savedUserName)
        outState.putString("email", savedEmail)
        outState.putString("password", savedPassword)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the saved data (username, email, password)
        savedInstanceState?.let {
            savedUserName = it.getString("username")
            savedEmail = it.getString("email")
            savedPassword = it.getString("password")
        }
    }
}

