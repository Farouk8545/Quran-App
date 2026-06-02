package com.example.quranapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.quranapp.presentation.screens.main_screens.AzkarScreen
import com.example.quranapp.presentation.screens.main_screens.DisplayAzkarScreen
import com.example.quranapp.presentation.screens.main_screens.DisplayDuaaScreen
import com.example.quranapp.presentation.screens.main_screens.DivisionsScreen
import com.example.quranapp.presentation.screens.main_screens.DuaaScreen
import com.example.quranapp.presentation.screens.main_screens.HomeScreen
import com.example.quranapp.presentation.screens.main_screens.PlaylistsScreen
import com.example.quranapp.presentation.screens.main_screens.QiblaScreen
import com.example.quranapp.presentation.screens.main_screens.QuranPlaylistScreen
import com.example.quranapp.presentation.screens.main_screens.QuranScreen
import com.example.quranapp.presentation.screens.main_screens.SettingsScreen
import com.example.quranapp.presentation.screens.main_screens.TextQuranScreen
import com.example.quranapp.presentation.screens.model.Screens

@Composable
fun MainNavigation(
    modifier: Modifier
) {
    val viewModel: MainNavigationViewModel = viewModel()
    var backStack = viewModel.backStack
    NavDisplay(
        modifier = modifier,
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
                QuranPlaylistScreen(it.metaData)
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
            entry <Screens.DuaaScreen>{
                DuaaScreen()
            }
            entry <Screens.DisplayDuaaScreen>{
                DisplayDuaaScreen(it.duaList, it.duaCategory)
            }
            entry <Screens.QiblaScreen>{
                QiblaScreen()
            }
        }
    )
}