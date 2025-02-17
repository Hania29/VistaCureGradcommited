package com.example.vistacuregrad.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.UserProfileRequest
import com.example.vistacuregrad.model.UserProfileResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class UserProfileViewModel(private val repository: AuthRepository, private val context: Context) : ViewModel() {

    private val _profileResponse = MutableLiveData<Response<UserProfileResponse>>()
    val profileResponse: LiveData<Response<UserProfileResponse>> get() = _profileResponse

    fun createUserProfile(request: UserProfileRequest) {
        viewModelScope.launch {
            try {
                val token = getOriginalToken()

                // Check if token is available
                if (token.isNullOrEmpty()) {
                    Log.e("UserProfileViewModel", "No original token found, please log in again.")
                    return@launch
                }

                // Proceed with the API call if the token is valid
                val response = repository.createUserProfile("Bearer $token", request)
                _profileResponse.postValue(response)

                if (response.isSuccessful) {
                    Log.d("UserProfileViewModel", "Profile created successfully: ${response.body()?.message}")
                } else {
                    when (response.code()) {
                        401 -> Log.e("UserProfileViewModel", "Unauthorized: Invalid token")
                        409 -> Log.e("UserProfileViewModel", "Conflict: Profile already exists")
                        400 -> Log.e("UserProfileViewModel", "BadRequest: Invalid profile data")
                        500 -> Log.e("UserProfileViewModel", "Internal Server Error: ${response.errorBody()?.string()}")
                        else -> Log.e("UserProfileViewModel", "Error: ${response.message()}")
                    }
                }

            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Exception during profile creation: ${e.message}", e)
            }
        }
    }

    private fun getOriginalToken(): String? {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("ORIGINAL_TOKEN", null)
    }
}


