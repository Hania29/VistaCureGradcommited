package com.example.vistacuregrad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.databinding.FragmentHomeBinding
import com.example.vistacuregrad.network.RetrofitClient
import com.example.vistacuregrad.viewmodel.HomeViewModel
import com.example.vistacuregrad.viewmodel.HomeViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // Initialize ViewModel
        val repository = AuthRepository(RetrofitClient.apiService)
        val factory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        // Set up UI components
        setupUI()
    }

    private fun setupUI() {
        // Disable icon tint for bottom navigation
        binding.bottomNavigationView.itemIconTintList = null

        // Set up drawer button click listener
        binding.materialButtondrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set up navigation drawer item click listener
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            handleNavigation(menuItem)
            true
        }

        // Set up bottom navigation item click listener
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            handleBottomNavigation(item)
            true
        }

        // Set up browse button click listener
        binding.browse.setOnClickListener {
            openImagePicker()
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
            R.id.homeFragment -> navController.navigate(R.id.homeFragment)
            R.id.chatBotFragment -> navController.navigate(R.id.action_homeFragment_to_chatBotFragment)
            R.id.historyFragment -> navController.navigate(R.id.action_homeFragment_to_historyFragment)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    getFileFromUri(requireContext(), uri)?.let { file ->
                        uploadImage(file)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Image selection cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uploadImage(file: File) {
        // Show progress bar
        binding.progressBar.visibility = View.VISIBLE

        // Prepare the image part for upload
        val requestFile = file.asRequestBody("image/*".toMediaType())
        val imagePart = MultipartBody.Part.createFormData("images", file.name, requestFile)

        // Upload the image
        homeViewModel.uploadImages(listOf(imagePart)) { response ->
            // Hide progress bar
            binding.progressBar.visibility = View.GONE

            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                Log.d("Upload", "Image uploaded successfully")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Toast.makeText(requireContext(), "Image upload failed: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.e("Upload", "Image upload failed: $errorMessage")
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return file
        } catch (e: Exception) {
            Log.e("FileCreation", "Error creating file from URI: ${e.message}")
            return null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
    }
}