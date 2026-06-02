package com.example.quranapp.domain.error_classes

sealed class NextPreviousError: Exception() {
    object FirstSurahReached: NextPreviousError() {
        private fun readResolve(): Any = FirstSurahReached
    }

    object LastSurahReached: NextPreviousError() {
        private fun readResolve(): Any = LastSurahReached
    }

    object NoActivePlaylist: NextPreviousError() {
        private fun readResolve(): Any = NoActivePlaylist
    }
}