package com.example.vistacuregrad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UploadResponse

import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HomeViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uploadResponse = MutableLiveData<UploadResponse?>()
    val uploadResponse: LiveData<UploadResponse?> get() = _uploadResponse

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch {
            val response = repository.uploadImage(file)
            if (response.isSuccessful) {
                _uploadResponse.value = response.body()
            } else {
                _uploadResponse.value = null // Handle error case
            }
        }
    }
}