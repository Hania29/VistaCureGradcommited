package com.example.vistacuregrad.Repository


import com.example.vistacuregrad.model.RegisterRequest
import com.example.vistacuregrad.model.RegisterResponse
import com.example.vistacuregrad.network.ApiService
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun registerUser(username: String, email: String, password: String): Response<RegisterResponse> {
        val request = RegisterRequest(username, email, password)  // ✅ Create correct request object
        return apiService.registerUser(request)


            }
        }