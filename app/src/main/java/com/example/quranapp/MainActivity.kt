package com.example.quranapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.data.local.dto.player.PlayerState
import com.example.quranapp.presentation.navigation.MainNavigation
import com.example.quranapp.presentation.navigation.MainNavigationViewModel
import com.example.quranapp.presentation.screens.helpercomposable.BottomNavBar
import com.example.quranapp.presentation.screens.helpercomposable.MiniPlayerDisplay
import com.example.quranapp.presentation.screens.helpercomposable.TopBar
import com.example.quranapp.presentation.screens.main_screens.PlayerBottomSheet
import com.example.quranapp.presentation.screens.model.Screens
import com.example.quranapp.presentation.ui_events.NextPreviousUIEvents
import com.example.quranapp.presentation.viewmodels.PlayerViewModel
import com.example.quranapp.ui.theme.QuranAppTheme
import com.example.quranapp.ui.theme.WhiteSmoke
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuranAppTheme {

                val navigationViewModel: MainNavigationViewModel = viewModel()
                val playerViewModel: PlayerViewModel = viewModel()

                val coroutineScope = rememberCoroutineScope()
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
                val snackBarState = remember { SnackbarHostState() }

                var playerState by remember {
                    mutableStateOf<PlayerState>(PlayerState())
                }
                var displayState by remember {
                    mutableStateOf<DisplayMetaData>(DisplayMetaData())
                }
                var playbackState by remember {
                    mutableStateOf<SliderAnimationSpecs>(SliderAnimationSpecs())
                }
                val context = LocalContext.current

                val bottomPaddingValues = if (playerState.isReady) PaddingValues(bottom = 100.dp) else PaddingValues(0.dp)

                LaunchedEffect(Unit) {
                    // Observe changes in player state
                    coroutineScope.launch {
                        playerViewModel.playerState().collect { collectedState ->
                            playerState = collectedState
                            if (playerState.playbackState == Player.STATE_ENDED){
                                playerViewModel.playNext()
                            }
                        }
                    }
                    // Observe changes in display state
                    coroutineScope.launch {
                        playerViewModel.displayState().collect { collectedState ->
                            displayState = collectedState
                        }
                    }
                    // Observe changes in error state
                    coroutineScope.launch {
                        playerViewModel.playerErrorState.collect { error ->
                            when (error){
                                is NextPreviousUIEvents.Error -> {
                                    snackBarState.currentSnackbarData?.dismiss()
                                    snackBarState.showSnackbar(
                                        message = context.getString(error.msg),
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    }
                    // Observe changes in playback state
                    coroutineScope.launch {
                        playerViewModel.sliderAnimationState.collect { collectedState ->
                            playbackState = collectedState
                        }
                    }
                }

                PlayerBottomSheet(
                    snackBarState = snackBarState,
                    displayMetaData = displayState,
                    isPlaying = playerState.isPlaying,
                    scaffoldState = bottomSheetScaffoldState,
                    coroutineScope = coroutineScope,
                    playbackState = playerState.playbackState,
                    sliderAnimationSpecs = playbackState,
                    currentPosition = {
                        playerViewModel.getCurrentPosition()
                    },
                    onNextClicked = {
                        playerViewModel.playNext()
                    },
                    onPlayClicked = {
                        playerViewModel.togglePlayPause()
                    },
                    onPreviousClicked = {
                        playerViewModel.playPrevious()
                    },
                    seekTo = { fraction ->
                        playerViewModel.seekTo(fraction)
                    },
                    canSeek = {
                        playerViewModel.canSeek()
                    }
                ) { outerPadding ->
                    Scaffold (
                        modifier = Modifier.fillMaxSize()
                            .padding(outerPadding),
                        // Show BottomNavBar in all screens except DisplayAzkarScreen and DisplayDuaaScreen
                        bottomBar = {
                            if (
                                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayAzkarScreen &&
                                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayDuaaScreen
                            ){
                                BottomNavBar(
                                    currentScreen = navigationViewModel.backStack.lastOrNull(),
                                ) {
                                    navigationViewModel.backStack.removeLastOrNull()
                                    navigationViewModel.backStack.add(it)
                                }
                            }
                        },
                        // Show TopBar in all screens except DisplayAzkarScreen and DisplayDuaaScreen
                        topBar = {
                            if (
                                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayAzkarScreen &&
                                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayDuaaScreen
                            ){
                                navigationViewModel.backStack.lastOrNull()
                                    ?.let { TopBar(screenName = stringResource(it.screenName)) }
                                    ?: TopBar("")
                            }
                        }
                    ){ innerPadding ->
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .padding(innerPadding)
                        ){
                            MainNavigation(
                                modifier = Modifier.padding(bottomPaddingValues)
                            )
                            // Show Player UI Display only if there's an initialized instance of the player (Audio playing)
                            if (playerState.isReady) {
                                MiniPlayerDisplay(
                                    modifier = Modifier.height(100.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .background(WhiteSmoke),
                                    displayMetaData = displayState,
                                    isPlaying = playerState.isPlaying,
                                    onPlayClicked = {
                                        playerViewModel.togglePlayPause()
                                    },
                                    onNextClicked = {
                                        playerViewModel.playNext()
                                    },
                                    onPreviousClicked = {
                                        playerViewModel.playPrevious()
                                    },
                                    onPlayerClicked = {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        }
                                    },
                                    onCloseClicked = {
                                        playerViewModel.releasePlayer()
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}