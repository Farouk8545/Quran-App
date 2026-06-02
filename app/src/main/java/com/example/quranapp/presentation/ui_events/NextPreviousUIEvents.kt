package com.example.quranapp.presentation.ui_events

import androidx.annotation.StringRes

sealed class NextPreviousUIEvents {
    data class Error(@StringRes val msg: Int): NextPreviousUIEvents()
}