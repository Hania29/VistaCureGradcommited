package com.example.vistacuregrad.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerResponse = MutableLiveData<Response<RegisterResponse>>()
    val registerResponse: LiveData<Response<RegisterResponse>> get() = _registerResponse

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(username, email, password)
                _registerResponse.postValue(response)
            } catch (e: Exception) {
                // Handle exceptions and show errors in logs
                e.printStackTrace()
            }
        }
    }
}
