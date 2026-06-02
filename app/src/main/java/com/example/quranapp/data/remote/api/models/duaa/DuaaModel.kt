package com.example.quranapp.data.remote.api.models.duaa

import com.example.quranapp.data.remote.api.models.azkar.SpecifiedAzkarModel

data class DuaaModel(
    val prophetic_duas: List<SpecifiedAzkarModel>,
    val quran_duas: List<SpecifiedAzkarModel>,
    val prophets_duas: List<SpecifiedAzkarModel>,
    val quran_completion_duas: List<SpecifiedAzkarModel>
)