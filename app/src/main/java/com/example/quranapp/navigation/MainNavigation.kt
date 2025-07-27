package com.example.quranapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.quranapp.screens.AzkarScreen
import com.example.quranapp.screens.DisplayAzkarScreen
import com.example.quranapp.screens.DivisionsScreen
import com.example.quranapp.screens.HomeScreen
import com.example.quranapp.screens.PlaylistsScreen
import com.example.quranapp.screens.QuranPlaylistScreen
import com.example.quranapp.screens.QuranScreen
import com.example.quranapp.screens.SettingsScreen
import com.example.quranapp.screens.TextQuranScreen
import com.example.quranapp.screens.model.Screens

@Composable
fun MainNavigation() {
    val viewModel: MainNavigationViewModel = viewModel()
    var backStack = viewModel.backStack
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider{
            entry<Screens.HomeScreen> {
                HomeScreen()
            }
            entry <Screens.QuranScreen>{
                QuranScreen()
            }
            entry <Screens.PlaylistsScreen>{
                PlaylistsScreen()
            }
            entry <Screens.DivisionsScreen>{
                DivisionsScreen()
            }
            entry <Screens.QuranPlaylistScreen>{
                QuranPlaylistScreen(it.reader, it.identifier)
            }
            entry <Screens.TextQuranScreen>{
                TextQuranScreen(it.surahNumber)
            }
            entry <Screens.SettingsScreen>{
                SettingsScreen()
            }
            entry <Screens.AzkarScreen>{
                AzkarScreen()
            }
            entry <Screens.DisplayAzkarScreen>{
                DisplayAzkarScreen(it.azkarList, it.azkarCategory)
            }
        }
    )
}