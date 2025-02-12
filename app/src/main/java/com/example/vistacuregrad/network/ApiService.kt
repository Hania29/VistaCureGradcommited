package com.example.vistacuregrad.network

import com.example.vistacuregrad.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("api/Authentication/Register")
    suspend fun registerUser(
        @Part("UserName") userName: RequestBody,
        @Part("Password") password: RequestBody,
        @Part("Email") email: RequestBody
    ): Response<RegisterResponse>
}
