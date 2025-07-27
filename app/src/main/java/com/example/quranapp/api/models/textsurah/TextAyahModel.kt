package com.example.quranapp.api.models.textsurah

data class TextAyahModel(
    val number: Int,
    val text: String,
    val numberInSurah: Int,
    val juz: Int,
    val hizbQuarter: Int,
    val page: Int,
    val ruku: Int,
    val manzil: Int,
)
