package com.example.vistacuregrad.network

import com.example.vistacuregrad.model.LoginResponse
import com.example.vistacuregrad.model.OtpRequest
import com.example.vistacuregrad.model.OtpResponse
import com.example.vistacuregrad.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
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

    @Multipart
    @POST("api/Authentication/Login")
    suspend fun loginUser(
        @Part("Username") username: RequestBody,
        @Part("Password") password: RequestBody
    ): Response<LoginResponse>

    @Multipart
    @POST("api/Authentication/VerifyOTP")
    suspend fun verifyOtp(
        @Part ("code") otpRequest: RequestBody,
        @Header("Authorization") token: String
    ): Response<OtpResponse>




}
