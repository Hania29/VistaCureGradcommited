package com.example.vistacuregrad.Mainactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.R

class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro, container, false)

        val btnGetStarted: Button = view.findViewById(R.id.btnGetStarted)
        val btnAlreadyHaveAccount0: Button = view.findViewById(R.id.btnAlreadyHaveAccount0)

        btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_fourthFragment)
        }

        btnAlreadyHaveAccount0.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_seventhFragment)
        }

        return view
    }
}
