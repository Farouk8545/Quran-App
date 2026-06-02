package com.example.quranapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.R
import com.example.quranapp.domain.use_case.surah.GetSurahMetaDataUseCase
import com.example.quranapp.presentation.ui_events.QuranPlaylistScreenUIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranPlaylistViewModel @Inject constructor(
    private val getSurahMetaDataUseCase: GetSurahMetaDataUseCase
): ViewModel() {

    private val _uiEvents = MutableStateFlow<QuranPlaylistScreenUIEvents>(QuranPlaylistScreenUIEvents.Loading)
    val uiEvents = _uiEvents.asStateFlow()

    // Fetch meta data upon creation
    init {
        getSurahMetaData()
    }

    fun getSurahMetaData() {
        viewModelScope.launch {
            try {
                val surahMetaData = getSurahMetaDataUseCase()
                _uiEvents.value = QuranPlaylistScreenUIEvents.Success(surahMetaData)
            }catch (e: Exception){
                Log.e("Quran Playlist Viewmodel", e.message.toString())
                _uiEvents.value = QuranPlaylistScreenUIEvents.Error(R.string.surah_meta_data_error_loading_message)
            }
        }
    }
}