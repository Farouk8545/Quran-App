package com.example.quranapp.api.models.textsurah

data class ApiTextSurahResponseModel(
    val code: Int,
    val status: String,
    val data: TextDataModel
)
