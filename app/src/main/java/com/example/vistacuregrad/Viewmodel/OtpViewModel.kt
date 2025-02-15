package com.example.vistacuregrad.Viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.OtpResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class OtpViewModel(application: Application, private val authRepository: AuthRepository) :
    AndroidViewModel(application) {

    private val _otpResponse = MutableLiveData<Response<OtpResponse>>()
    val otpResponse: LiveData<Response<OtpResponse>> = _otpResponse

    fun verifyOtp(code: String) {
        val token = getToken()

        if (token.isNullOrEmpty()) {
            Log.e("OtpViewModel", "Token is missing. User needs to log in again.")
            return
        }

        viewModelScope.launch {
            try {
                val response = authRepository.verifyOtp(code, token)
                _otpResponse.postValue(response)
            } catch (e: Exception) {
                Log.e("OtpViewModel", "Error verifying OTP: ${e.message}")
                _otpResponse.postValue(Response.error(400, okhttp3.ResponseBody.create(null, "Error verifying OTP")))
            }
        }
    }

    private fun getToken(): String? {
        val sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", null)
    }
}



