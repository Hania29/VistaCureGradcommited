package com.example.vistacuregrad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vistacuregrad.Newactivity.Model.VcareItem

class VcareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vcare, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        val itemList = listOf(
            VcareItem("How it Works?", isHeader = true),
            VcareItem("Take a Clear Photo:", "Ensure the photo is well-lit and in focus."),
            VcareItem("Upload Image:", "Select the 'Upload Image' button and choose the photo."),
            VcareItem("Prediction:", "The app will analyze the uploaded image using AI."),
            VcareItem("Results:", "The app will display a list of potential eye diseases, ranked by probability."),

            VcareItem("Disclaimer!", "This app provides a preliminary assessment. It is not a substitute for professional medical advice.", isDisclaimer = true),

            VcareItem("Important Notes:", isHeader = true),
            VcareItem("Accuracy:", "The accuracy of the predictions depends on the quality of the image and the complexity of the eye condition."),
            VcareItem("Medical Consultation:", "The results provided by this app are for informational purposes only. Always consult with a qualified ophthalmologist for diagnosis and treatment."),
            VcareItem("Data Privacy:", "We are committed to protecting your privacy. Uploaded images may be used to improve the accuracy of the app’s algorithms, but all personal information will be kept confidential."),

            VcareItem("Tips for Optimal Image Capture:", isHeader = true),
            VcareItem("Good Lighting:", "Use natural light or bright, even artificial lighting. Avoid shadows."),
            VcareItem("Clear Focus:", "Ensure the entire eye is in focus, including the pupil and surrounding areas."),
            VcareItem("Straight Position:", "Keep the camera level with the eye to avoid distortion."),
            VcareItem("Close-up:", "Take a close-up photo of the eye, but ensure the entire eye is visible."),

            VcareItem("Disclaimer!", "This app is intended for informational purposes only and should not be considered a substitute for professional medical advice. Always consult with a qualified healthcare provider for any health concerns.", isDisclaimer = true)
        )

        val adapter = VcareAdapter(itemList)
        recyclerView.adapter = adapter


        val backButton: View = view.findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        return view
    }

}
