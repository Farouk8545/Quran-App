package com.example.quranapp.api.models.textsurah

import com.example.quranapp.api.models.surah.EditionModel

data class TextDataModel(
    val surahs: List<TextSurahModel>,
    val edition: EditionModel

)
