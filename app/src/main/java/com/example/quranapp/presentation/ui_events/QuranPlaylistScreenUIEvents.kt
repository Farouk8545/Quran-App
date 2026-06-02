package com.example.quranapp.presentation.ui_events

import androidx.annotation.StringRes
import com.example.quranapp.data.local.dto.surahs.SurahMetaData

sealed class QuranPlaylistScreenUIEvents {
    data object Loading: QuranPlaylistScreenUIEvents()
    data class Success(val surahMetaData: List<SurahMetaData>): QuranPlaylistScreenUIEvents()
    data class Error(@StringRes val msg: Int): QuranPlaylistScreenUIEvents()
}