package com.example.quranapp.presentation.screens.main_screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.presentation.screens.helpercomposable.FullPlayerDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    snackBarState: SnackbarHostState,
    displayMetaData: DisplayMetaData,
    isPlaying: Boolean,
    scaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    playbackState: Int,
    sliderAnimationSpecs: SliderAnimationSpecs,
    currentPosition: () -> Long,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    seekTo: (fraction: Float) -> Unit,
    canSeek: () -> Boolean,
    content: @Composable (PaddingValues) -> Unit
){

    // Creates a bottom sheet to host the full UI for the player
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(snackBarState) },
        sheetSwipeEnabled = true,
        sheetPeekHeight = 0.dp,
        sheetDragHandle = null,
        sheetShape = RectangleShape,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        sheetContent = {

            // Actual content of the full player UI
            FullPlayerDisplay(
                reciterName = displayMetaData.reciterName,
                reciterImageUrl = displayMetaData.reciterImageUrl,
                surahName = displayMetaData.surahName,
                isPlaying = isPlaying,
                playbackState = playbackState,
                sliderAnimationSpecs = sliderAnimationSpecs,
                currentPosition = currentPosition,
                onBackClicked = { coroutineScope.launch { scaffoldState.bottomSheetState.partialExpand() } },
                onNextClicked = {
                    onNextClicked()
                },
                onPlayClicked = {
                    onPlayClicked()
                },
                onPreviousClicked = {
                    onPreviousClicked()
                },
                seekTo = seekTo,
                canSeek = canSeek
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}