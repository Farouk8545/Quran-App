package com.example.quranapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.R
import com.example.quranapp.data.remote.api.models.azkar.FullAzkarModel
import com.example.quranapp.data.remote.api.models.azkar.SpecifiedAzkarModel
import com.example.quranapp.data.remote.api.models.duaa.DuaaModel
import com.example.quranapp.data.remote.api.models.meta.ApiMetaResponseModel
import com.example.quranapp.data.remote.api.models.quran.TextSurah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _textSurah = MutableStateFlow<TextSurah?>(null)
    val textSurah = _textSurah.asStateFlow()

    lateinit var metaData: ApiMetaResponseModel

    private lateinit var azkar: FullAzkarModel

    private lateinit var textQuran: List<TextSurah>

    private lateinit var duaa: DuaaModel

    init {
        viewModelScope.launch {
            azkar = loadAzkar()
            textQuran = loadTextQuran()
            metaData = loadMeta()
            duaa = loadDuaa()
        }
    }

    fun getTextSurah(surah: Int){
        _textSurah.value = null
        _textSurah.value = textQuran[surah - 1]
    }

    fun loadAzkar(): FullAzkarModel {
        val jsonString = context.assets.open("azkar.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, FullAzkarModel::class.java)
    }

    fun loadTextQuran(): List<TextSurah> {
        val jsonString = context.assets.open("quran.json").bufferedReader().use { it.readText() }

        val listType = object : TypeToken<List<TextSurah>>() {}.type

        return Gson().fromJson(jsonString, listType)
    }

    fun loadMeta(): ApiMetaResponseModel {
        val jsonString = context.assets.open("meta.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, ApiMetaResponseModel::class.java)
    }

    fun loadDuaa(): DuaaModel {
        val jsonString = context.assets.open("duaa.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, DuaaModel::class.java)
    }

    fun getAzkar(azkarCategory: Int): List<SpecifiedAzkarModel>? {
        return when(azkarCategory){
            R.string.azkar_morning -> azkar.morning_azkar
            R.string.azkar_evening -> azkar.evening_azkar
            R.string.azkar_prayer -> azkar.prayer_azkar
            R.string.azkar_prayer_later -> azkar.prayer_later_azkar
            R.string.azkar_sleep -> azkar.sleep_azkar
            R.string.azkar_wake_up -> azkar.wake_up_azkar
            R.string.azkar_mosque -> azkar.mosque_azkar
            R.string.azkar_misc -> azkar.miscellaneous_azkar
            R.string.azkar_adhan -> azkar.adhan_azkar
            R.string.azkar_wudu -> azkar.wudu_azkar
            R.string.azkar_home -> azkar.home_azkar
            R.string.azkar_khala -> azkar.khala_azkar
            R.string.azkar_food -> azkar.food_azkar
            R.string.azkar_hajj_umrah -> azkar.hajj_and_umrah_azkar
            else -> emptyList()
        }
    }

    fun getDuaa(duaaCategory: Int): List<SpecifiedAzkarModel>? {
        return when(duaaCategory){
            R.string.prophetic_duas -> duaa.prophetic_duas
            R.string.quran_duas -> duaa.quran_duas
            R.string.prophets_duas -> duaa.prophets_duas
            R.string.quran_completion_duas -> duaa.quran_completion_duas
            else -> emptyList()

        }
    }

}