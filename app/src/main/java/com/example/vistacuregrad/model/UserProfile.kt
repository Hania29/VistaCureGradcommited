package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class UserProfileRequest(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("DateOfBirth") val dateOfBirth: String,
    @SerializedName("Height") val height: Double,
    @SerializedName("Weight") val weight: Double,
    @SerializedName("Gender") val gender: String
)


data class UserProfileResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)
