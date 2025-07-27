package com.example.quranapp.api.models.quran

data class ApiQuranResponseModel(
    val code: Int,
    val status: String,
    val data: QuranDataModel
)
