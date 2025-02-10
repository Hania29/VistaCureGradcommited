package com.example.vistacuregrad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.databinding.FragmentUserDrawerBinding
import java.util.regex.Pattern

class UserDrawerFragment : Fragment(R.layout.fragment_user_drawer) {

    private var _binding: FragmentUserDrawerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {
            if (validateFields()) {
                Toast.makeText(context, "All fields are valid.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Validation
    private fun validateFields(): Boolean {
        val firstname = binding.firstnameUser.text.toString().trim()
        val lastname = binding.lastnameUser.text.toString().trim()
        val birthday = binding.editTextText7.text.toString().trim()
        val weight = binding.editTextText8.text.toString().trim()
        val height = binding.editTextText9.text.toString().trim()
        val gender = binding.drGender.text.toString().trim()

        var isValid = true

        // First name validation (not empty and only letters)
        if (firstname.isEmpty()) {
            binding.firstnameUser.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z]+$", firstname)) {
            binding.firstnameUser.error = "First name should only contain letters"
            isValid = false
        }

        // Last name validation (not empty and only letters)
        if (lastname.isEmpty()) {
            binding.lastnameUser.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z]+$", lastname)) {
            binding.lastnameUser.error = "Last name should only contain letters"
            isValid = false
        }

        // Date of birth validation (not empty and correct format MM/DD/YYYY)
        val datePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")  // Format MM/DD/YYYY
        if (birthday.isEmpty()) {
            binding.editTextText7.error = "This field is required"
            isValid = false
        } else if (!datePattern.matcher(birthday).matches()) {
            binding.editTextText7.error = "Invalid date format. Use MM/DD/YYYY"
            isValid = false
        }

        // Weight validation (not empty and only numbers)
        if (weight.isEmpty()) {
            binding.editTextText8.error = "This field is required"
            isValid = false
        } else if (!weight.matches("\\d+".toRegex())) {
            binding.editTextText8.error = "Weight should be a number"
            isValid = false
        }

        // Height validation (not empty and only numbers)
        if (height.isEmpty()) {
            binding.editTextText9.error = "This field is required"
            isValid = false
        } else if (!height.matches("\\d+".toRegex())) {
            binding.editTextText9.error = "Height should be a number"
            isValid = false
        }

        // Gender validation (not empty and only letters)
        val genderGroup: RadioGroup = binding.Radiogender
        val selectedGenderId = genderGroup.checkedRadioButtonId
        if (selectedGenderId == -1) {
            Toast.makeText(context, "Please select a gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Avoid memory leaks by setting binding to null after view is destroyed
    }
}
