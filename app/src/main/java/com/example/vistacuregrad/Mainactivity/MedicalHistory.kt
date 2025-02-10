package com.example.vistacuregrad.Mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vistacuregrad.Newactivity.NewActivity
import com.example.vistacuregrad.R
import java.text.SimpleDateFormat
import java.util.Locale

class MedicalHistory : Fragment() {

    private var savedAllergies: String? = null
    private var savedChronicConditions: String? = null
    private var savedMedications: String? = null
    private var savedSurgeries: String? = null
    private var savedFamilyHistory: String? = null
    private var savedCheckupDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medical_history, container, false)

        val btnDone: Button = view.findViewById(R.id.btndone)
        val btnBack: Button = view.findViewById(R.id.btnBack)

        val etAllergies: EditText = view.findViewById(R.id.etAllergies)
        val etChronicConditions: EditText = view.findViewById(R.id.etchronicconditions)
        val etMedications: EditText = view.findViewById(R.id.etmedications)
        val etSurgeries: EditText = view.findViewById(R.id.etsurgeries)
        val etFamilyHistory: EditText = view.findViewById(R.id.etfamilyhistory)
        val etCheckupDate: EditText = view.findViewById(R.id.etcheckupdate)

        savedInstanceState?.let {
            savedAllergies = it.getString("allergies")
            savedChronicConditions = it.getString("chronicConditions")
            savedMedications = it.getString("medications")
            savedSurgeries = it.getString("surgeries")
            savedFamilyHistory = it.getString("familyHistory")
            savedCheckupDate = it.getString("checkupDate")

            etAllergies.setText(savedAllergies)
            etChronicConditions.setText(savedChronicConditions)
            etMedications.setText(savedMedications)
            etSurgeries.setText(savedSurgeries)
            etFamilyHistory.setText(savedFamilyHistory)
            etCheckupDate.setText(savedCheckupDate)
        }

        btnDone.setOnClickListener {
            val allergies = etAllergies.text.toString().trim()
            val chronicConditions = etChronicConditions.text.toString().trim()
            val medications = etMedications.text.toString().trim()
            val surgeries = etSurgeries.text.toString().trim()
            val familyHistory = etFamilyHistory.text.toString().trim()
            val checkupDate = etCheckupDate.text.toString().trim()

            if (allergies.isEmpty() || chronicConditions.isEmpty() || medications.isEmpty() ||
                surgeries.isEmpty() || familyHistory.isEmpty() || checkupDate.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidDate(checkupDate)) {
                etCheckupDate.error = "Enter a valid date in the format Day/Month/Year"
                etCheckupDate.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(requireActivity(), NewActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            val parsedDate = dateFormat.parse(date)
            parsedDate != null
        } catch (e: Exception) {
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("allergies", savedAllergies)
        outState.putString("chronicConditions", savedChronicConditions)
        outState.putString("medications", savedMedications)
        outState.putString("surgeries", savedSurgeries)
        outState.putString("familyHistory", savedFamilyHistory)
        outState.putString("checkupDate", savedCheckupDate)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            savedAllergies = it.getString("allergies")
            savedChronicConditions = it.getString("chronicConditions")
            savedMedications = it.getString("medications")
            savedSurgeries = it.getString("surgeries")
            savedFamilyHistory = it.getString("familyHistory")
            savedCheckupDate = it.getString("checkupDate")
        }
    }
}
