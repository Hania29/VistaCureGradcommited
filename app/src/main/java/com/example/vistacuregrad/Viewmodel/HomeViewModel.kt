package com.example.vistacuregrad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class HomeViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uploadResponse = MutableLiveData<Response<UploadResponse>>()
    val uploadResponse: LiveData<Response<UploadResponse>> get() = _uploadResponse

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(file)
                _uploadResponse.value = response
            } catch (e: Exception) {
                _uploadResponse.value = null
            }
        }
    }
}
