package com.example.vistacuregrad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vistacuregrad.Newactivity.Model.EyeCareTip

class VcareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vcare, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemList = listOf(
            EyeCareTip("Regular Eye Exams", "Schedule comprehensive eye exams at least once a year."),
            EyeCareTip("Healthy Diet", "Eat leafy greens, fish, and citrus fruits for better eye health."),
            EyeCareTip("Protect Your Eyes from the Sun", "Wear sunglasses with UVA and UVB protection."),
            EyeCareTip("Avoid Smoking", "Smoking increases the risk of eye diseases like macular degeneration."),
            EyeCareTip("Maintain a Healthy Weight", "Obesity can increase the risk of eye conditions."),

            EyeCareTip("Tips for Digital Eye Strain", isHeader = true),
            EyeCareTip("Follow the 20-20-20 Rule", "Every 20 minutes, look at something 20 feet away for 20 seconds."),
            EyeCareTip("Adjust Screen Brightness", "Reduce digital eye strain by using the right brightness settings."),
            EyeCareTip("Take Regular Breaks", "Give your eyes a break every 30 minutes."),
            EyeCareTip("Increase Text Size", "Larger text reduces strain on your eyes."),
            EyeCareTip("Use a Blue Light Filter", "Enable blue light filter settings to reduce strain."),

            EyeCareTip("Additional Tips", isHeader = true),
            EyeCareTip("Wear Protective Eyewear", "Use protective glasses while working with screens or in bright environments."),
            EyeCareTip("Practice Good Contact Lens Hygiene", "Follow your doctor’s guidelines on cleaning contact lenses."),
            EyeCareTip("Be Aware of Symptoms", "If you experience changes in vision, see an eye doctor immediately."),

            EyeCareTip("Disclaimer!", "This information is not a substitute for professional medical advice. Always consult an eye specialist.", isDisclaimer = true)
        )

        val adapter = EyeCareTipsAdapter(itemList)
        recyclerView.adapter = adapter

        val backButton: View = view.findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }
}
