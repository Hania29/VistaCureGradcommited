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
                    val token = response.body()?.token

                    if (!token.isNullOrEmpty()) {
                        saveToken(token)

                    } else {
                        Log.e("LoginViewModel", "Token is null or empty")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginViewModel", "Login failed: $errorBody")
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("TOKEN", token).commit()
        Log.d("LoginViewModel", "Token saved: $token")
    }
    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", null)
    }


}

