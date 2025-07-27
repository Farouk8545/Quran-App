package com.example.quranapp.api.models.surah

data class DataModel(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int,
    val ayahs: List<AyahModel>,
    val edition: EditionModel
)
