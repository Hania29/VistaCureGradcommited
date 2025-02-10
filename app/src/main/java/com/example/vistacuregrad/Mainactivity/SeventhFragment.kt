package com.example.vistacuregrad.Mainactivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
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

class SeventhFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private var savedUsername: String? = null
    private var savedPassword: String? = null
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)

        val etUserName0: EditText = view.findViewById(R.id.etUserName0)
        val etPassword: EditText = view.findViewById(R.id.etPassword)
        val ivTogglePassword: ImageView = view.findViewById(R.id.hidddenEye)
        val btnSignUp: Button = view.findViewById(R.id.btnSignUp)
        val btnForgotPassword: Button = view.findViewById(R.id.btnForgotPassword)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val btnLogIn: Button = view.findViewById(R.id.btnLogIn)

        // Restore saved data if available
        savedInstanceState?.let {
            savedUsername = it.getString("username")
            savedPassword = it.getString("password")

            etUserName0.setText(savedUsername)
            etPassword.setText(savedPassword)
        }

        // Set up password visibility toggle
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = togglePasswordVisibility(etPassword, ivTogglePassword, isPasswordVisible)
        }

        btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_seventhFragment_to_fourthFragment)
        }

        btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_seventhFragment_to_fifthFragment)
        }

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnLogIn.setOnClickListener {
            val enteredUsername = etUserName0.text.toString().trim()
            val enteredPassword = etPassword.text.toString().trim()

            // Retrieve saved credentials
            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (validateFields(etUserName0, etPassword)) {
                if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                    findNavController().navigate(R.id.action_seventhFragment_to_sixthFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please correct the errors", Toast.LENGTH_SHORT).show()
            }
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

    private fun validateFields(username: EditText, password: EditText): Boolean {
        val usernameText = username.text.toString().trim()
        val passwordText = password.text.toString().trim()

        if (TextUtils.isEmpty(usernameText)) {
            username.error = "Username cannot be empty"
            username.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(passwordText)) {
            password.error = "Password cannot be empty"
            password.requestFocus()
            return false
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the entered username and password when rotating
        outState.putString("username", savedUsername)
        outState.putString("password", savedPassword)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the saved data (username and password)
        savedInstanceState?.let {
            savedUsername = it.getString("username")
            savedPassword = it.getString("password")
        }
    }
}


