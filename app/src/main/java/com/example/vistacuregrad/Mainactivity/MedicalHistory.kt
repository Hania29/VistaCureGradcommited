package com.example.vistacuregrad.Mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistacuregrad.R
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.MedicalHistoryRequest
import com.example.vistacuregrad.network.RetrofitClient
import com.example.vistacuregrad.viewmodel.MedicalHistoryViewModel
import com.example.vistacuregrad.viewmodel.MedicalHistoryViewModelFactory
import com.example.vistacuregrad.Newactivity.NewActivity
import java.text.SimpleDateFormat
import java.util.*

class MedicalHistory : Fragment() {

    private lateinit var viewModel: MedicalHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_medical_history, container, false)

        // Initialize UI elements
        val btnDone: Button = view.findViewById(R.id.btndone)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val etAllergies: EditText = view.findViewById(R.id.etAllergies)
        val etChronicConditions: EditText = view.findViewById(R.id.etchronicconditions)
        val etMedications: EditText = view.findViewById(R.id.etmedications)
        val etSurgeries: EditText = view.findViewById(R.id.etsurgeries)
        val etFamilyHistory: EditText = view.findViewById(R.id.etfamilyhistory)
        val etCheckupDate: EditText = view.findViewById(R.id.etcheckupdate)

        // Initialize ViewModel
        val apiService = RetrofitClient.apiService  // Get ApiService instance
        val repository = AuthRepository(apiService) // Pass ApiService to Repository
        val factory = MedicalHistoryViewModelFactory(repository, requireContext()) // Pass repository and context
        viewModel = ViewModelProvider(this, factory)[MedicalHistoryViewModel::class.java]

        btnDone.setOnClickListener {
            val allergies = etAllergies.text.toString().trim()
            val chronicConditions = etChronicConditions.text.toString().trim()
            val medications = etMedications.text.toString().trim()
            val surgeries = etSurgeries.text.toString().trim()
            val familyHistory = etFamilyHistory.text.toString().trim()
            val checkupDate = etCheckupDate.text.toString().trim()

            // Validate user inputs
            if (allergies.isEmpty() || chronicConditions.isEmpty() || medications.isEmpty() ||
                surgeries.isEmpty() || familyHistory.isEmpty() || checkupDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate Date of Birth format
            if (!isValidDate(checkupDate)) {
                etCheckupDate.error = "Enter a valid date (dd/MM/yyyy)"
                etCheckupDate.requestFocus()
                return@setOnClickListener
            }

            // Create medical history request object
            val request = MedicalHistoryRequest(
                allergies = allergies,
                chronicConditions = chronicConditions,
                medications = medications,
                surgeries = surgeries,
                familyHistory = familyHistory,
                lastCheckupDate = checkupDate
            )

            // Call ViewModel function to create medical history
            viewModel.createMedicalHistory(request)
        }

        // Observe API response
        viewModel.medicalHistoryResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Medical history created successfully!", Toast.LENGTH_SHORT).show()

                // Intent to navigate to NewActivity
                val intent = Intent(requireActivity(), NewActivity::class.java)
                startActivity(intent)
                requireActivity().finish()  // Close the current fragment and activity
            } else {
                Toast.makeText(requireContext(), "Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
            }
        })

        // Back button click listener
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
