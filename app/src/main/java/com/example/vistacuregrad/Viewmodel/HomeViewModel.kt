package com.example.vistacuregrad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class HomeViewModel(private val repository: AuthRepository) : ViewModel() {

    fun uploadImages(images: List<MultipartBody.Part>, onResult: (Response<UploadResponse>) -> Unit) {
        viewModelScope.launch {
            val response = repository.uploadImages(images)
            onResult(response)
        }
    }
}
