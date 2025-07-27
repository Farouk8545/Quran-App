package com.example.quranapp.api.models.surah

data class EditionModel(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String
)