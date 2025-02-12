package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("UserName") val userName: String,  // Ensure correct field name
    @SerializedName("Email") val email: String,
    @SerializedName("Password") val password: String
)

data class RegisterResponse(
    @SerializedName("status")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
