package com.example.vistacuregrad

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.databinding.FragmentChatBotBinding

class ChatBotFragment : Fragment(R.layout.fragment_chat_bot) {

    private var _binding: FragmentChatBotBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatBotBinding.bind(view)

        binding.bottomNavigationView.itemIconTintList = null


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            handleBottomNavigation(item)
            true
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.btnEye.setOnClickListener {
            sendMessage()
        }
    }

    private fun handleBottomNavigation(item: MenuItem) {
        val navController = findNavController()
        when (item.itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
                binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
            }
            R.id.chatBotFragment -> {
                binding.bottomNavigationView.menu.findItem(R.id.chatBotFragment).isChecked = true
            }
            R.id.historyFragment -> {
                navController.navigate(R.id.action_chatBotFragment_to_historyFragment)
                binding.bottomNavigationView.menu.findItem(R.id.historyFragment).isChecked = true
            }
        }
    }


    private fun sendMessage() {
        val message = binding.etMessage.text.toString()

        if (message.isNotBlank()) {

            Toast.makeText(requireContext(), "Message Sent: $message", Toast.LENGTH_SHORT).show()


            binding.etMessage.text.clear()
        } else {
            Toast.makeText(requireContext(), "Please enter a message", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        binding.bottomNavigationView.menu.findItem(R.id.chatBotFragment).isChecked = true
    }
}
