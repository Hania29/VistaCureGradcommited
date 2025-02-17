package com.example.vistacuregrad.Viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistacuregrad.Repository.AuthRepository
import com.example.vistacuregrad.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: AuthRepository, private val context: Context) : ViewModel() {

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>> get() = _loginResponse

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(username, password)
                _loginResponse.postValue(response)

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LoginViewModel", "Login Response: $loginResponse")

                    val tempToken = loginResponse?.tempToken
                    if (!tempToken.isNullOrEmpty()) {
                        saveTempToken(tempToken)
                        Log.d("LoginViewModel", "TempToken saved successfully: $tempToken")
                    } else {
                        Log.e("LoginViewModel", "TempToken is null or empty")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginViewModel", "Login failed: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}", e)
            }
        }
    }

    private fun saveTempToken(token: String) {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("TEMP_TOKEN", token).apply()
    }

    fun getTempToken(): String? {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TEMP_TOKEN", null)
    }
}
