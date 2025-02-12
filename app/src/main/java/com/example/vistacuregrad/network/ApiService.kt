package com.example.vistacuregrad.network

import com.example.vistacuregrad.model.RegisterRequest
import com.example.vistacuregrad.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/Authentication/Register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}
