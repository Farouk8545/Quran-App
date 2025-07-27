package com.example.quranapp.screens.model

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.example.quranapp.R
import com.example.quranapp.api.models.azkar.SpecifiedAzkarModel
import kotlinx.serialization.Serializable

sealed class Screens(@StringRes internal val screenName: Int): NavKey{

    @Serializable
    data object HomeScreen: Screens(R.string.home_screen)

    @Serializable
    data object QuranScreen: Screens(R.string.quran_screen)

    @Serializable
    data object PlaylistsScreen: Screens(R.string.playlist_screen)

    @Serializable
    data object DivisionsScreen: Screens(R.string.division_screen)

    @Serializable
    data class QuranPlaylistScreen(val reader: String, val identifier: String): Screens(R.string.quran_playlist_screen)

    @Serializable
    data class TextQuranScreen(val surahNumber: Int): Screens(R.string.quran_screen)

    @Serializable
    data object SettingsScreen: Screens(R.string.settings)

    @Serializable
    data object AzkarScreen: Screens(R.string.azkar)

    @Serializable
    data class DisplayAzkarScreen(val azkarList: List<SpecifiedAzkarModel>, val azkarCategory: Int): Screens(R.string.azkar)
}
