package com.example.vistacuregrad.model

data class UploadResponse(
    val status: String,
    val message: String,
    val results: List<Result>
)

data class Result(
    val diseaseName: String,
    val probability: Float
)
