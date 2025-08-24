package com.example.quranapp.api.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.R
import com.example.quranapp.api.models.azkar.FullAzkarModel
import com.example.quranapp.api.models.azkar.SpecifiedAzkarModel
import com.example.quranapp.api.models.duaa.DuaaModel
import com.example.quranapp.api.models.edition.ApiEditionResponseModel
import com.example.quranapp.api.models.meta.ApiMetaResponseModel
import com.example.quranapp.api.models.quran.TextSurah
import com.example.quranapp.api.models.quran.Verse
import com.example.quranapp.api.models.surah.ApiSurahResponseModel
import com.example.quranapp.api.models.textsurah.ApiTextSurahResponseModel
import com.example.quranapp.api.models.textsurah.TextSurahModel
import com.example.quranapp.api.repository.ApiRepository
import com.example.quranapp.player.AudioPlayerService
import com.example.quranapp.player.PlayerViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val repository: ApiRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var isEditionLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)

    var showBottomSheet by mutableStateOf(false)
        private set

    private val  _surahState = MutableStateFlow<ApiSurahResponseModel?>(null)
    var surahState = _surahState.asStateFlow()

    private val _textSurah = MutableStateFlow<TextSurah?>(null)
    val textSurah = _textSurah.asStateFlow()

    private val _edition = MutableStateFlow<ApiEditionResponseModel?>(null)
    var edition = _edition.asStateFlow()

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

    fun getSurah(surah: Int, edition: String?){
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.getSurah(surah, edition)
                if (response.isSuccessful){
                    _surahState.value = response.body()
                    if (showBottomSheet == false){
                        AudioPlayerService.instance?.createNotification(
                            _surahState.value?.data?.name ?: "Surah",
                            _surahState.value?.data?.edition?.name ?: "Reader",
                            true)
                    }
                    showBottomSheet = true
                }else{
                    errorMessage = response.message()
                }
            }catch (e: IOException){
                errorMessage = e.message
            }catch (e: Exception){
                errorMessage = e.message
            }finally {
                isLoading = false
            }
        }
    }

    fun getTextSurah(surah: Int){
        _textSurah.value = null
        _textSurah.value = textQuran[surah - 1]
    }

    fun getEditions(
        format: String = "audio",
        language: String = "ar",
        type: String = "versebyverse"
    ){
        viewModelScope.launch {
            if (_edition.value == null){
                isEditionLoading = true
                try {
                    val response = repository.getEditions(format, language, type)
                    if (response.isSuccessful){
                        _edition.value = response.body()
                    }else{
                        errorMessage = response.message()
                    }
                }catch (e: IOException){
                    errorMessage = e.message
                }catch (e: Exception){
                    errorMessage = e.message
                }finally {
                    isEditionLoading = false
                }
            }
        }
    }

    fun loadAzkar(): FullAzkarModel{
        val jsonString = context.assets.open("azkar.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, FullAzkarModel::class.java)
    }

    fun loadTextQuran(): List<TextSurah> {
        val jsonString = context.assets.open("quran.json").bufferedReader().use { it.readText() }

        val listType = object : TypeToken<List<TextSurah>>() {}.type

        return Gson().fromJson(jsonString, listType)
    }

    fun loadMeta(): ApiMetaResponseModel{
        val jsonString = context.assets.open("meta.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, ApiMetaResponseModel::class.java)
    }

    fun loadDuaa(): DuaaModel{
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

    fun playNext(){
        _surahState.value?.data.let {
            if (it?.number != null){
                getSurah(it.number + 1, it.edition.identifier)
            }
        }
    }

    fun playPrevious(){
        _surahState.value?.data.let {
            if (it?.number != null){
                getSurah(it.number - 1, it.edition.identifier)
            }
        }
    }

    fun observeSurahEnded(playerViewModel: PlayerViewModel){
        viewModelScope.launch {
            playerViewModel.surahEnded.collect {
                playNext()
            }
        }
    }

}