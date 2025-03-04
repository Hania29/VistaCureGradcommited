package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class UserProfileLogRequest(
    @SerializedName("FirstName") val firstName: String = "",
    @SerializedName("LastName") val lastName: String = "",
    @SerializedName("DateOfBirth") val dateOfBirth: String = "",
    @SerializedName("Height") val height: Double = 0.0,
    @SerializedName("Weight") val weight: Double = 0.0,
    @SerializedName("Gender") val gender: String = "Unknown"
)


data class UserProfileLogResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserProfileLogData?
)

data class UserProfileLogData(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("DateOfBirth") val dateOfBirth: String,
    @SerializedName("Height") val height: Double,
    @SerializedName("Weight") val weight: Double,
    @SerializedName("Gender") val gender: String
)
