package com.example.quranapp.api.models.surah

data class AyahModel(
    val number: Int,
    val audio: String,
    val secondaudioSecondary: List<String>,
    val text: String,
    val numberInSurah: Int,
    val juz: Int,
    val manzil: Int,
    val page: Int,
    val ruku: Int,
    val hizbQuarter: Int
)
