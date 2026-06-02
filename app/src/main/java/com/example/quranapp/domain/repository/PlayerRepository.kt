package com.example.quranapp.domain.repository

import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.data.local.dto.player.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val playerState: StateFlow<PlayerState>
    val displayState: StateFlow<DisplayMetaData>
    val sliderAnimationState: StateFlow<SliderAnimationSpecs>
    fun play(surahNumber: Int, reciterUrlIdentifier: String)
    fun togglePlayPause()
    fun playNext(): Result<Unit>
    fun playPrevious(): Result<Unit>
    fun getCurrentPosition(): Long
    fun seekTo(fraction: Float)
    fun canSeek(): Boolean
    fun release()
}