package com.example.vistacuregrad.Newactivity.Model


data class AboutItem(
    val title: String,
    val description: String = "",
    val isHeader: Boolean = false,
    val isDisclaimer: Boolean = false
)
