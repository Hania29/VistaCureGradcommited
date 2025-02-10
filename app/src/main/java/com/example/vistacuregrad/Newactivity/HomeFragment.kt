package com.example.vistacuregrad

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.bottomNavigationView.itemIconTintList = null


        binding.materialButtondrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigation(menuItem)
            true
        }


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            handleBottomNavigation(item)
            true

        }

    }

    private fun handleNavigation(menuItem: MenuItem) {
        val navController = findNavController()
        when (menuItem.itemId) {
            R.id.userDrawer -> navController.navigate(R.id.action_homeFragment_to_userDrawer)
            R.id.medicalDrawer -> navController.navigate(R.id.action_homeFragment_to_medicalDrawer)
            R.id.vcare -> navController.navigate(R.id.action_homeFragment_to_vcare)
            R.id.about -> navController.navigate(R.id.action_homeFragment_to_about)
            R.id.help -> navController.navigate(R.id.action_homeFragment_to_help)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun handleBottomNavigation(item: MenuItem) {
        val navController = findNavController()
        when (item.itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
                binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
            }
            R.id.chatBotFragment -> {
                navController.navigate(R.id.action_homeFragment_to_chatBotFragment)
                binding.bottomNavigationView.menu.findItem(R.id.chatBotFragment).isChecked = true
            }
            R.id.historyFragment -> {
                navController.navigate(R.id.action_homeFragment_to_historyFragment)
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
        // Ensure the correct menu item is highlighted when returning to Home
        binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
    }
}
