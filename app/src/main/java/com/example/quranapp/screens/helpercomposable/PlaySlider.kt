package com.example.quranapp.screens.helpercomposable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.C
import androidx.media3.common.Player
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.player.PlayerViewModel
import com.example.quranapp.ui.theme.AppPurple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySlider(){

    val playerViewModel: PlayerViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()

    var isDragging by remember { mutableStateOf(false) }
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val surah by apiViewModel.surahState.collectAsState()

    val animatedProgress = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val passedAyahs = (playerViewModel.sliderModel.currentAyah.toFloat()) / (surah?.data?.ayahs?.size?.toFloat() ?: 1f)
        if (playerViewModel.player.playbackState == Player.STATE_READY && playerViewModel.player.duration != C.TIME_UNSET){
            val progressInCurrentAyah = (playerViewModel.player.currentPosition.toFloat() / playerViewModel.player.duration.toFloat()) / (surah?.data?.ayahs?.size?.toFloat() ?: 1f)
            animatedProgress.snapTo(passedAyahs + progressInCurrentAyah)
        }else{
            animatedProgress.snapTo(passedAyahs)
        }
        apiViewModel.observeSurahEnded(playerViewModel)
    }

    LaunchedEffect(playerViewModel.sliderModel.currentAyah, isPlaying) {
        if (!isPlaying){
            animatedProgress.stop()
            return@LaunchedEffect
        }
        if(playerViewModel.player.playbackState == Player.STATE_READY &&
            playerViewModel.player.duration != C.TIME_UNSET &&
            !isDragging
            ){
            if (((playerViewModel.sliderModel.currentAyah.toFloat() + 1) / (surah?.data?.ayahs?.size?.toFloat() ?: 1f)) < animatedProgress.value){
                animatedProgress.snapTo(playerViewModel.sliderModel.currentAyah.toFloat() / (surah?.data?.ayahs?.size?.toFloat() ?: 1f))
            }
            animatedProgress.animateTo(
                targetValue = ((playerViewModel.sliderModel.currentAyah.toFloat() + 1) / (surah?.data?.ayahs?.size?.toFloat() ?: 1f)),
                animationSpec = tween(
                    durationMillis = playerViewModel.sliderModel.ayahDuration.toInt() - playerViewModel.player.currentPosition.toInt(),
                    easing = LinearEasing
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                        playerViewModel.pause()
                        val ayahIndex = (
                                animatedProgress.value * (surah?.data?.ayahs?.size ?: 1)).toInt()
                                                        .coerceIn(0, surah?.data?.ayahs?.size?.minus(1) ?: 1
                                )
                        playerViewModel.seekTo(ayahIndex)
                        coroutineScope.launch{
                            animatedProgress.snapTo(ayahIndex.toFloat() / (surah?.data?.ayahs?.size?.toFloat() ?: 1f))
                        }
                        playerViewModel.resume()
                    },
                    onDragCancel = {
                        isDragging = false
                    },
                    onDrag = { change,_ ->
                        change.consume()
                        val positionX = change.position.x.coerceIn(0f, size.width.toFloat())
                        val progress = positionX / size.width
                        coroutineScope.launch {
                            animatedProgress.snapTo(progress)
                        }
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = 8.dp.toPx()

            // Background
            drawRoundRect(
                color = Color.Gray.copy(alpha = 0.3f),
                size = Size(width, height),
                topLeft = Offset(0f, (size.height - height) / 2),
                cornerRadius = CornerRadius(8.dp.toPx())
            )

            // Played
            drawRoundRect(
                color = AppPurple,
                size = Size(animatedProgress.value * width, height),
                topLeft = Offset(0f, (size.height - height) / 2),
                cornerRadius = CornerRadius(8.dp.toPx())
            )

            drawCircle(
                color = AppPurple,
                radius = 8.dp.toPx(),
                center = Offset(animatedProgress.value * (width - 8.dp.toPx()), size.height / 2),
            )

            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(animatedProgress.value * (width - 8.dp.toPx()), size.height / 2),
            )
        }
    }
}