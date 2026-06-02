package com.example.quranapp.data.local.dto.reciters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class ReciterMetaData(
    @SerialName("reciter_id") val reciterId: Int,
    @SerialName("reciter_name_ar") val reciterNameAr: String,
    @SerialName("reciter_name_en") val reciterNameEn: String,
    @SerialName("reciter_url_identifier") val reciterUrlIdentifier: String,
    @SerialName("reciter_image_url_reference") val reciterUrlImage: String
) {
    val displayName: String
        get() {
            val currentLanguage = Locale.getDefault().language
            return if (currentLanguage == "ar") reciterNameAr else reciterNameEn
        }
}