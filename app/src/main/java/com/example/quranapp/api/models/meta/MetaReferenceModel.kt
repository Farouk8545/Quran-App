package com.example.quranapp.api.models.meta

data class MetaReferenceModel(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
)
