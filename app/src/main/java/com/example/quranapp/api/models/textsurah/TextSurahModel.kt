package com.example.quranapp.api.models.textsurah

data class TextSurahModel(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    var ayahs: List<TextAyahModel>
)
