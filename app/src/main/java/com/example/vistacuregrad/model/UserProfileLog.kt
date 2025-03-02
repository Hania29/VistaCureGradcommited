package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class UserProfileLogRequest(
    @SerializedName("FirstName") val firstName: String? = null,
    @SerializedName("LastName") val lastName: String? = null,
    @SerializedName("DateOfBirth") val dateOfBirth: String? = null,
    @SerializedName("Height") val height: Double? = null,
    @SerializedName("Weight") val weight: Double? = null,
    @SerializedName("Gender") val gender: String? = null
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
