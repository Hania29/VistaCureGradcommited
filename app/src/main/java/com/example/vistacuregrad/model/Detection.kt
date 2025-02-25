package com.example.vistacuregrad.model

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("results") val results: List<DiseaseResult>?
)

data class DiseaseResult(
    @SerializedName("diseaseName") val diseaseName: String,
    @SerializedName("probability") val probability: Double
)
