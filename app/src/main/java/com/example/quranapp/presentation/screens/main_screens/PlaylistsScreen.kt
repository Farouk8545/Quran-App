package com.example.quranapp.presentation.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.domain.language.LanguageViewModel
import com.example.quranapp.presentation.navigation.MainNavigationViewModel
import com.example.quranapp.presentation.screens.helpercomposable.LoadingScreen
import com.example.quranapp.presentation.screens.helpercomposable.PlaylistCard
import com.example.quranapp.presentation.screens.model.Screens
import com.example.quranapp.presentation.ui_events.PlaylistScreenUIEvents
import com.example.quranapp.presentation.viewmodels.ApiViewModel
import com.example.quranapp.presentation.viewmodels.PlaylistScreenViewModel

@Composable
fun PlaylistsScreen(){

    val playlistVM: PlaylistScreenViewModel = viewModel()
    val navViewModel: MainNavigationViewModel = viewModel()

    val uiState by playlistVM.uIEvents.collectAsState()

    when (uiState){
        is PlaylistScreenUIEvents.Error -> {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(stringResource((uiState as PlaylistScreenUIEvents.Error).res))
            }
        }
        is PlaylistScreenUIEvents.Loading -> {
            LoadingScreen()
        }
        is PlaylistScreenUIEvents.Success -> {
            Box (
                modifier = Modifier.padding(24.dp)
            ){
                LazyColumn {
                    items((uiState as PlaylistScreenUIEvents.Success).metaData) { reciterEntry ->
                        PlaylistCard(reciterEntry) {
                            navViewModel.backStack.add(Screens.QuranPlaylistScreen(reciterEntry))
                        }
                    }
                }
            }
        }
    }
}