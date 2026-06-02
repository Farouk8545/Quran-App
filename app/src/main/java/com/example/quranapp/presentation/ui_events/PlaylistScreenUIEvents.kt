package com.example.quranapp.presentation.ui_events

import androidx.annotation.StringRes
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData

sealed class PlaylistScreenUIEvents {
    data object Loading: PlaylistScreenUIEvents()
    data class Success(val metaData: List<ReciterMetaData>): PlaylistScreenUIEvents()
    data class Error(@StringRes val res: Int): PlaylistScreenUIEvents()
}