package com.example.vistacuregrad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.databinding.FragmentMedicalDrawerBinding
import java.util.regex.Pattern

class MedicalDrawerFragment : Fragment(R.layout.fragment_medical_drawer) {

    private var _binding: FragmentMedicalDrawerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicalDrawerBinding.inflate(inflater, container, false)
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


    private fun validateFields(): Boolean {
        val allergies = binding.Allergies.text.toString().trim()
        val chronicConditions = binding.chronicConditions.text.toString().trim()
        val medication = binding.medication.text.toString().trim()
        val surgeries = binding.surgeries.text.toString().trim()
        val familyHistory = binding.familyhistory.text.toString().trim()
        val lastCheckup = binding.lastcheckup.text.toString().trim()

        var isValid = true


        if (allergies.isEmpty()) {
            binding.Allergies.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z\\s]+$", allergies)) {
            binding.Allergies.error = "Allergies should only contain letters and spaces"
            isValid = false
        }


        if (chronicConditions.isEmpty()) {
            binding.chronicConditions.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z\\s]+$", chronicConditions)) {
            binding.chronicConditions.error = "Chronic conditions should only contain letters and spaces"
            isValid = false
        }


        if (medication.isEmpty()) {
            binding.medication.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z\\s]+$", medication)) {
            binding.medication.error = "Medication should only contain letters and spaces"
            isValid = false
        }


        if (surgeries.isEmpty()) {
            binding.surgeries.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z\\s]+$", surgeries)) {
            binding.surgeries.error = "Surgeries should only contain letters and spaces"
            isValid = false
        }


        if (familyHistory.isEmpty()) {
            binding.familyhistory.error = "This field is required"
            isValid = false
        } else if (!Pattern.matches("^[a-zA-Z\\s]+$", familyHistory)) {
            binding.familyhistory.error = "Family history should only contain letters and spaces"
            isValid = false
        }

        val datePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")
        if (lastCheckup.isEmpty()) {
            binding.lastcheckup.error = "This field is required"
            isValid = false
        } else if (!datePattern.matcher(lastCheckup).matches()) {
            binding.lastcheckup.error = "Invalid date format. Use MM/DD/YYYY"
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
