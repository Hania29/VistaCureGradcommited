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

    private val _diseaseResult = MutableLiveData<Response<UploadResponse>>()
    val diseaseResult: LiveData<Response<UploadResponse>> get() = _diseaseResult

    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(image)
                _diseaseResult.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
