package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R
import java.text.SimpleDateFormat
import java.util.Locale

class UserProfile : Fragment() {

    private var firstName: String? = null
    private var lastName: String? = null
    private var dateOfBirth: String? = null
    private var height: String? = null
    private var weight: String? = null
    private var selectedGender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        // Initialize buttons, radio group, and edit texts
        val btnNext: Button = view.findViewById(R.id.btnNext)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val rgGender: RadioGroup = view.findViewById(R.id.rgGender)
        val etFirstName: EditText = view.findViewById(R.id.firstname)
        val etLastName: EditText = view.findViewById(R.id.Lastname)
        val etDateOfBirth: EditText = view.findViewById(R.id.Dateofbirth)
        val etHeight: EditText = view.findViewById(R.id.etHeight)
        val etWeight: EditText = view.findViewById(R.id.etWeight)

        // Restore the saved values if available
        savedInstanceState?.let {
            firstName = it.getString("firstName")
            lastName = it.getString("lastName")
            dateOfBirth = it.getString("dateOfBirth")
            height = it.getString("height")
            weight = it.getString("weight")
            selectedGender = it.getString("selectedGender")

            etFirstName.setText(firstName)
            etLastName.setText(lastName)
            etDateOfBirth.setText(dateOfBirth)
            etHeight.setText(height)
            etWeight.setText(weight)

            // Restore the selected gender radio button
            selectedGender?.let { gender ->
                if (gender == "Male") {
                    view.findViewById<RadioButton>(R.id.rbMale).isChecked = true
                } else if (gender == "Female") {
                    view.findViewById<RadioButton>(R.id.rbFemale).isChecked = true
                }
            }
        }

        btnNext.setOnClickListener {
            // Retrieve input from edit texts
            firstName = etFirstName.text.toString().trim()
            lastName = etLastName.text.toString().trim()
            dateOfBirth = etDateOfBirth.text.toString().trim()
            height = etHeight.text.toString().trim()
            weight = etWeight.text.toString().trim()

            // Validate the fields
            if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || dateOfBirth.isNullOrEmpty() || height.isNullOrEmpty() || weight.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Date of Birth validation
            if (TextUtils.isEmpty(dateOfBirth)) {
                etDateOfBirth.error = "Date of Birth is required"
                etDateOfBirth.requestFocus()
                return@setOnClickListener
            } else if (!isValidDate(dateOfBirth!!)) {
                etDateOfBirth.error = "Enter a valid date in the format Day/Month/Year"
                etDateOfBirth.requestFocus()
                return@setOnClickListener
            }

            // Validate radio group selection
            val selectedId = rgGender.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(requireContext(), "Please select your gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrieve the selected gender
            val selectedRadioButton: RadioButton = view.findViewById(selectedId)
            selectedGender = selectedRadioButton.text.toString()

            // Navigate to the next screen
            findNavController().navigate(R.id.action_userProfile_to_medicalHistory)
        }

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    // Function to validate the date format
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
        // Save the values of the form fields
        outState.putString("firstName", firstName)
        outState.putString("lastName", lastName)
        outState.putString("dateOfBirth", dateOfBirth)
        outState.putString("height", height)
        outState.putString("weight", weight)
        outState.putString("selectedGender", selectedGender)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the saved values when the screen is rotated
        savedInstanceState?.let {
            firstName = it.getString("firstName")
            lastName = it.getString("lastName")
            dateOfBirth = it.getString("dateOfBirth")
            height = it.getString("height")
            weight = it.getString("weight")
            selectedGender = it.getString("selectedGender")
        }
    }
}


