package com.example.quranapp.api.models.quran

import com.example.quranapp.api.models.surah.DataModel
import com.example.quranapp.api.models.surah.EditionModel

data class QuranDataModel(
    val surahs: List<DataModel>,
    val edition: EditionModel
)
