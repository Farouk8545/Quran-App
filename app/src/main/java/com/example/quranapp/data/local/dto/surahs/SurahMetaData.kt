package com.example.quranapp.data.local.dto.surahs

import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class SurahMetaData(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
) {
    val displayName: String
        get() {
            val currentLanguage = Locale.getDefault().language
            return if (currentLanguage == "ar") name else englishName
        }
}