package com.example.quranapp.api.models.azkar

data class FullAzkarModel(
    val morning_azkar: List<SpecifiedAzkarModel>,
    val evening_azkar: List<SpecifiedAzkarModel>,
    val prayer_azkar: List<SpecifiedAzkarModel>,
    val prayer_later_azkar: List<SpecifiedAzkarModel>,
    val sleep_azkar: List<SpecifiedAzkarModel>,
    val wake_up_azkar: List<SpecifiedAzkarModel>,
    val mosque_azkar: List<SpecifiedAzkarModel>,
    val miscellaneous_azkar: List<SpecifiedAzkarModel>,
    val adhan_azkar: List<SpecifiedAzkarModel>,
    val wudu_azkar: List<SpecifiedAzkarModel>,
    val home_azkar: List<SpecifiedAzkarModel>,
    val khala_azkar: List<SpecifiedAzkarModel>,
    val food_azkar: List<SpecifiedAzkarModel>,
    val hajj_and_umrah_azkar: List<SpecifiedAzkarModel>
)
