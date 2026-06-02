package com.example.quranapp.data.remote.api.models.quran

data class TextSurah(
    val id: Int,
    val name: String,
    val translation: String,
    val type: String,
    val total_verses: Int,
    val verses: List<Verse>
)
