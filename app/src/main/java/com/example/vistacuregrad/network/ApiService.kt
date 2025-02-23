package com.example.vistacuregrad.network

import com.example.vistacuregrad.model.ForgotPasswordResponse
import com.example.vistacuregrad.model.LoginResponse
import com.example.vistacuregrad.model.MedicalHistoryResponse
import com.example.vistacuregrad.model.OtpRequest
import com.example.vistacuregrad.model.OtpResponse
import com.example.vistacuregrad.model.RegisterResponse
import com.example.vistacuregrad.model.ResetPasswordResponse
import com.example.vistacuregrad.model.UserProfileResponse
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
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("api/Authentication/Register")
    suspend fun registerUser(
        @Part("UserName") userName: RequestBody,
        @Part("Password") password: RequestBody,
        @Part("Email") email: RequestBody
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("api/Authentication/Login")
    suspend fun loginUser(
        @Field("Username") username: String,
        @Field("Password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("api/Authentication/VerifyOTP")
    suspend fun verifyOtp(
        @Field("code") code: String,
        @Header("Authorization") token: String
    ): Response<OtpResponse>

    @Multipart
    @POST("api/Authentication/CreateUserProfile")
    suspend fun createUserProfile(
        @Header("Authorization") token: String,  // For JWT authentication
        @Part("FirstName") firstName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("DateOfBirth") dateOfBirth: RequestBody,
        @Part("Height") height: RequestBody,
        @Part("Weight") weight: RequestBody,
        @Part("Gender") gender: RequestBody
    ): Response<UserProfileResponse>

    @Multipart
    @POST("api/Authentication/MedicalHistory")
    suspend fun createMedicalHistory(
        @Header("Authorization") token: String,  // For JWT authentication
        @Part("allergies") allergies: RequestBody,
        @Part("chronicConditions") chronicConditions: RequestBody,
        @Part("medications") medications: RequestBody,
        @Part("surgeries") surgeries: RequestBody,
        @Part("familyHistory") familyHistory: RequestBody,
        @Part("lastCheckupDate") lastCheckupDate: RequestBody
    ): Response<MedicalHistoryResponse>

    @FormUrlEncoded
    @POST("api/Authentication/forgetPasswordlogin")
    suspend fun forgotPassword(
        @Field("email") email: String
    ): Response<ForgotPasswordResponse>


    @FormUrlEncoded
    @POST("api/Authentication/ResetPasswordlogin")
    suspend fun resetPassword(
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
        @Query("token") token: String,
        @Field("email") email: String
    ): Response<ResetPasswordResponse>


}
