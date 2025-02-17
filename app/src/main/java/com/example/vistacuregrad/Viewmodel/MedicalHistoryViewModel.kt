package com.example.vistacuregrad.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.MedicalHistoryRequest
import com.example.vistacuregrad.model.MedicalHistoryResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MedicalHistoryViewModel(private val repository: AuthRepository, private val context: Context) : ViewModel() {

    private val _medicalHistoryResponse = MutableLiveData<Response<MedicalHistoryResponse>>()
    val medicalHistoryResponse: LiveData<Response<MedicalHistoryResponse>> get() = _medicalHistoryResponse

    fun createMedicalHistory(request: MedicalHistoryRequest) {
        viewModelScope.launch {
            try {
                val token = getOriginalToken()

                // Check if token is available
                if (token.isNullOrEmpty()) {
                    Log.e("MedicalHistoryViewModel", "No original token found, please log in again.")
                    return@launch
                }

                // Proceed with the API call if the token is valid
                val response = repository.createMedicalHistory("Bearer $token", request)
                _medicalHistoryResponse.postValue(response)

                if (response.isSuccessful) {
                    Log.d("MedicalHistoryViewModel", "Medical history created successfully: ${response.body()?.message}")
                } else {
                    when (response.code()) {
                        401 -> Log.e("MedicalHistoryViewModel", "Unauthorized: Invalid token")
                        409 -> Log.e("MedicalHistoryViewModel", "Conflict: Medical history already exists")
                        400 -> Log.e("MedicalHistoryViewModel", "BadRequest: Invalid medical history data")
                        500 -> Log.e("MedicalHistoryViewModel", "Internal Server Error: ${response.errorBody()?.string()}")
                        else -> Log.e("MedicalHistoryViewModel", "Error: ${response.message()}")
                    }
                }

            } catch (e: Exception) {
                Log.e("MedicalHistoryViewModel", "Exception during medical history creation: ${e.message}", e)
            }
        }
    }

    private fun getOriginalToken(): String? {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("ORIGINAL_TOKEN", null)
    }
}
