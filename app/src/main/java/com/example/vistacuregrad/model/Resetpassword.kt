package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("password") val password: String,
    @SerializedName("confirmPassword") val confirmPassword: String,
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String
)
data class ResetPasswordResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)