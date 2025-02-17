package com.example.vistacuregrad.Repository

import android.util.Log
import com.example.vistacuregrad.model.LoginResponse
import com.example.vistacuregrad.model.MedicalHistoryRequest
import com.example.vistacuregrad.model.MedicalHistoryResponse
import com.example.vistacuregrad.model.OtpResponse
import com.example.vistacuregrad.model.RegisterResponse
import com.example.vistacuregrad.model.UserProfileRequest
import com.example.vistacuregrad.model.UserProfileResponse
import com.example.vistacuregrad.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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


        return apiService.loginUser(username, password)
    }


    suspend fun verifyOtp(code: String, token: String): Response<OtpResponse> {

        Log.d("AautoRrepository", "code: $code | token: '$token'")
        return apiService.verifyOtp(code, token)
    }

    suspend fun createUserProfile(token: String, request: UserProfileRequest): Response<UserProfileResponse> {
        // Convert request fields to RequestBody using the same pattern as registerUser
        val firstNameBody = request.firstName.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastNameBody = request.lastName.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateOfBirthBody = request.dateOfBirth.toRequestBody("text/plain".toMediaTypeOrNull())
        val heightBody = request.height.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val weightBody = request.weight.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderBody = request.gender.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call the API service method to create the user profile
        return apiService.createUserProfile(
            token = token,
            firstName = firstNameBody,
            lastName = lastNameBody,
            dateOfBirth = dateOfBirthBody,
            height = heightBody,
            weight = weightBody,
            gender = genderBody
        )
    }
    suspend fun createMedicalHistory(token: String, request: MedicalHistoryRequest): Response<MedicalHistoryResponse> {
        // Convert fields to RequestBody
        val allergiesBody = request.allergies.toRequestBody("text/plain".toMediaTypeOrNull())
        val chronicConditionsBody = request.chronicConditions.toRequestBody("text/plain".toMediaTypeOrNull())
        val medicationsBody = request.medications.toRequestBody("text/plain".toMediaTypeOrNull())
        val surgeriesBody = request.surgeries.toRequestBody("text/plain".toMediaTypeOrNull())
        val familyHistoryBody = request.familyHistory.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastCheckupDateBody = request.lastCheckupDate.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call the API to create medical history
        return apiService.createMedicalHistory(
            token = token,
            allergies = allergiesBody,
            chronicConditions = chronicConditionsBody,
            medications = medicationsBody,
            surgeries = surgeriesBody,
            familyHistory = familyHistoryBody,
            lastCheckupDate = lastCheckupDateBody
        )
    }



}










