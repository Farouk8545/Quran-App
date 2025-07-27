package com.example.quranapp.api.models.duaa

import com.example.quranapp.api.models.azkar.SpecifiedAzkarModel

data class DuaaModel(
    val prophetic_duas: List<SpecifiedAzkarModel>,
    val quran_duas: List<SpecifiedAzkarModel>,
    val prophets_duas: List<SpecifiedAzkarModel>,
    val quran_completion_duas: List<SpecifiedAzkarModel>
)