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

class OtpViewModel(application: Application, private val authRepository: AuthRepository) : AndroidViewModel(application) {

    private val _otpResponse = MutableLiveData<Response<OtpResponse>>()
    val otpResponse: LiveData<Response<OtpResponse>> = _otpResponse

    fun verifyOtp(code: String) {
        val token = getTempToken()

        if (token.isNullOrEmpty()) {
            Log.e("OtpViewModel", "Temp token is missing. User needs to log in again.")
            return
        }

        viewModelScope.launch {
            try {
                val response = authRepository.verifyOtp(code, "Bearer $token")
                _otpResponse.postValue(response)

                if (response.isSuccessful) {
                    val otpResponse = response.body()
                    val originalToken = otpResponse?.token // Assuming the response has the original token
                    if (!originalToken.isNullOrEmpty()) {
                        saveOriginalToken(originalToken)
                        Log.d("OtpViewModel", "Original token saved successfully: $originalToken")
                    } else {
                        Log.e("OtpViewModel", "Original token is null or empty")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OtpViewModel", "OTP verification failed: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("OtpViewModel", "Error verifying OTP: ${e.message}")
            }
        }
    }

    private fun saveOriginalToken(token: String) {
        val sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ORIGINAL_TOKEN", token).apply() // Save original token
    }

    private fun getTempToken(): String? {
        val sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TEMP_TOKEN", null)
    }
}

