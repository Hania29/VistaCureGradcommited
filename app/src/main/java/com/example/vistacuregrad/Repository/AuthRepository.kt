package com.example.vistacuregrad.Repository

import android.content.Context
import android.util.Log
import com.example.vistacuregrad.model.LoginResponse
import com.example.vistacuregrad.model.OtpRequest
import com.example.vistacuregrad.model.OtpResponse
import com.example.vistacuregrad.model.RegisterResponse
import com.example.vistacuregrad.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response


class AuthRepository(private val apiService: ApiService) {

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Response<RegisterResponse> {
        val userNameBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        return apiService.registerUser(
            userName = userNameBody,
            password = passwordBody,
            email = emailBody
        )
    }

    suspend fun loginUser(username: String, password: String): Response<LoginResponse> {
        val usernameBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        return apiService.loginUser(
            username = usernameBody,
            password = passwordBody
        )
    }


    suspend fun verifyOtp(code: String, token: String): Response<OtpResponse> {
        Log.d("AautoRrepository", "code: $code | token: '$token'")
        return apiService.verifyOtp(
            OtpRequest(code),
            token = token
        )
    }


}










