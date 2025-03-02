package com.example.vistacuregrad.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UserProfileLogRequest
import com.example.vistacuregrad.model.UserProfileLogResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class UserProfileLogViewModel(private val repository: AuthRepository, private val context: Context) : ViewModel() {

    private val _profileLogResponse = MutableLiveData<Response<UserProfileLogResponse>>()
    val profileLogResponse: LiveData<Response<UserProfileLogResponse>> get() = _profileLogResponse

    fun getUserProfileLog() {
        viewModelScope.launch {
            try {
                val token = getOriginalToken()
                if (token.isNullOrEmpty()) {
                    Log.e("UserProfileLogViewModel", "No original token found, please log in again.")
                    return@launch
                }

                val response = repository.getUserProfileLog(token)
                _profileLogResponse.postValue(response)

                if (response.isSuccessful) {
                    Log.d("UserProfileLogViewModel", "Profile fetched successfully: ${response.body()?.message}")
                } else {
                    Log.e("UserProfileLogViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UserProfileLogViewModel", "Exception during profile fetch: ${e.message}", e)
            }
        }
    }

    fun updateUserProfileLog(request: UserProfileLogRequest) {
        viewModelScope.launch {
            try {
                val token = getOriginalToken()
                if (token.isNullOrEmpty()) {
                    Log.e("UserProfileLogViewModel", "No original token found, please log in again.")
                    return@launch
                }

                val response = repository.updateUserProfileLog(token, request)
                _profileLogResponse.postValue(response)

                if (response.isSuccessful) {
                    Log.d("UserProfileLogViewModel", "Profile updated successfully: ${response.body()?.message}")
                } else {
                    Log.e("UserProfileLogViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UserProfileLogViewModel", "Exception during profile update: ${e.message}", e)
            }
        }
    }

    fun deleteUserProfileLog() {
        viewModelScope.launch {
            try {
                val token = getOriginalToken()
                if (token.isNullOrEmpty()) {
                    Log.e("UserProfileLogViewModel", "No original token found, please log in again.")
                    return@launch
                }

                val response = repository.deleteUserProfileLog(token)
                _profileLogResponse.postValue(response)

                if (response.isSuccessful) {
                    Log.d("UserProfileLogViewModel", "Profile deleted successfully: ${response.body()?.message}")
                } else {
                    Log.e("UserProfileLogViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UserProfileLogViewModel", "Exception during profile deletion: ${e.message}", e)
            }
        }
    }

    private fun getOriginalToken(): String? {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("ORIGINAL_TOKEN", null)
    }
}