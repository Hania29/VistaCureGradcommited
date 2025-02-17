package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UserProfileRequest
import com.example.vistacuregrad.network.RetrofitClient
import com.example.vistacuregrad.viewmodel.UserProfileViewModel
import com.example.vistacuregrad.viewmodel.UserProfileViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class UserProfile : Fragment() {

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        // Initialize UI elements
        val btnNext: Button = view.findViewById(R.id.btnNext)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val rgGender: RadioGroup = view.findViewById(R.id.rgGender)
        val etFirstName: EditText = view.findViewById(R.id.firstname)
        val etLastName: EditText = view.findViewById(R.id.Lastname)
        val etDateOfBirth: EditText = view.findViewById(R.id.Dateofbirth)
        val etHeight: EditText = view.findViewById(R.id.etHeight)
        val etWeight: EditText = view.findViewById(R.id.etWeight)

        // ✅ FIXED: Correct ViewModel Initialization
        val apiService = RetrofitClient.apiService  // Get ApiService instance
        val repository = AuthRepository(apiService) // Pass ApiService to Repository
        val factory = UserProfileViewModelFactory(repository, requireContext()) // Pass repository and context
        viewModel = ViewModelProvider(this, factory)[UserProfileViewModel::class.java]

        btnNext.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val dateOfBirth = etDateOfBirth.text.toString().trim()
            val heightStr = etHeight.text.toString().trim()
            val weightStr = etWeight.text.toString().trim()

            // Validate user inputs
            if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() ||
                heightStr.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate Date of Birth format
            if (!isValidDate(dateOfBirth)) {
                etDateOfBirth.error = "Enter a valid date (dd/MM/yyyy)"
                etDateOfBirth.requestFocus()
                return@setOnClickListener
            }

            // Validate gender selection
            val selectedGenderId = rgGender.checkedRadioButtonId
            if (selectedGenderId == -1) {
                Toast.makeText(requireContext(), "Please select your gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedGender = view.findViewById<RadioButton>(selectedGenderId).text.toString()

            // Convert height and weight to double
            val height = heightStr.toDoubleOrNull()
            val weight = weightStr.toDoubleOrNull()

            if (height == null || height <= 0 || weight == null || weight <= 0) {
                Toast.makeText(requireContext(), "Invalid height or weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user profile request object
            val request = UserProfileRequest(
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = dateOfBirth,
                height = height,
                weight = weight,
                gender = selectedGender
            )

            // Call ViewModel function to create user profile
            viewModel.createUserProfile(request)
        }

        // Observe API response
        viewModel.profileResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Profile created successfully!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_userProfile_to_medicalHistory)
            } else {
                Toast.makeText(requireContext(), "Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
            }
        })

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    // Function to validate the date format
    private fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }
}
