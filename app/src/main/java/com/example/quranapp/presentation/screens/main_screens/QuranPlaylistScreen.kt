package com.example.quranapp.presentation.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.PlayerState
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.presentation.screens.helpercomposable.LoadingScreen
import com.example.quranapp.presentation.screens.helpercomposable.SurahCard
import com.example.quranapp.presentation.ui_events.QuranPlaylistScreenUIEvents
import com.example.quranapp.presentation.viewmodels.PlayerViewModel
import com.example.quranapp.presentation.viewmodels.QuranPlaylistViewModel
import kotlinx.coroutines.launch

@Composable
fun QuranPlaylistScreen(
    reciterMetaData: ReciterMetaData
){
    val playerViewModel: PlayerViewModel = viewModel()
    val quranPlaylistVM: QuranPlaylistViewModel = viewModel()

    val uiState by quranPlaylistVM.uiEvents.collectAsState()
    var displayState by remember {
        mutableStateOf<DisplayMetaData>(DisplayMetaData())
    }
    var playerState by remember {
        mutableStateOf<PlayerState>(PlayerState())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            playerViewModel.displayState().collect { collectedState ->
                displayState = collectedState
            }
        }
        coroutineScope.launch {
            playerViewModel.playerState().collect { collectedState ->
                playerState = collectedState
            }
        }
    }

    when (uiState){
        // Error loading meta data
        is QuranPlaylistScreenUIEvents.Error -> {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(stringResource((uiState as QuranPlaylistScreenUIEvents.Error).msg))
            }
        }
        // Loading indicator until data is fetched
        is QuranPlaylistScreenUIEvents.Loading -> {
            LoadingScreen()
        }
        // Success loading data, display screen content
        is QuranPlaylistScreenUIEvents.Success -> {
            Box (
                modifier = Modifier.fillMaxSize()
                    .padding(24.dp)
            ){
                LazyColumn (
                    modifier = Modifier.fillMaxSize()
                ){
                    items((uiState as QuranPlaylistScreenUIEvents.Success).surahMetaData){surah ->
                        val isSelected = (surah.number == displayState.surahNumber) &&
                                        (reciterMetaData.reciterUrlIdentifier == displayState.reciterUrlIdentifier)
                        val isPlaying = playerState.isPlaying
                        SurahCard(
                            surah.displayName,
                            reciterMetaData,
                            isPlaying,
                            isSelected
                        ) {
                            if (isSelected){
                                playerViewModel.togglePlayPause()
                            }else{
                                playerViewModel.play(surah.number, reciterMetaData.reciterUrlIdentifier)
                            }
//                            apiViewModel.getSurah(surah.number, reciterMetaData.reciterUrlIdentifier)
//                            scope.launch {
//                                val exist = databaseViewModel.isSurahInLastListenedTo(surah.number, reciterMetaData.reciterUrlIdentifier)
//                                if (exist == true){
//                                    databaseViewModel.changeTimestamp(surah.number, reciterMetaData.reciterUrlIdentifier)
//                                    databaseViewModel.getAllFromLastListenedToSurah()
//                                }else{
//                                    databaseViewModel.addSurahToLastListenedTo(
//                                        DatabaseAudioSurah(
//                                            surahNameAr = surah.name,
//                                            surahNameEn = surah.englishName,
//                                            surahNumber = surah.number,
//                                            readerNameAr = reciterMetaData.reciterNameAr,
//                                            readerNameEn = reciterMetaData.reciterNameEn,
//                                            identifier = reciterMetaData.reciterUrlIdentifier
//                                        )
//                                    )
//                                    databaseViewModel.getAllFromLastListenedToSurah()
//                                }
//                            }
                        }
                    }
                }
            }
        }
    }
}