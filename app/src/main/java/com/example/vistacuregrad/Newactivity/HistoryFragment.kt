package com.example.vistacuregrad

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)


        binding.bottomNavigationView.itemIconTintList = null


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.Delete.setOnClickListener {

            Toast.makeText(requireContext(), "History deleted", Toast.LENGTH_SHORT).show()


        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            handleBottomNavigation(item)
            true
        }
    }

    private fun handleBottomNavigation(item: MenuItem) {
        val navController = findNavController()
        when (item.itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
            }
            R.id.chatBotFragment -> {
                navController.navigate(R.id.action_historyFragment_to_chatBotFragment)
            }
            R.id.historyFragment -> {

                binding.bottomNavigationView.menu.findItem(R.id.historyFragment).isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        binding.bottomNavigationView.menu.findItem(R.id.historyFragment).isChecked = true
    }
}
