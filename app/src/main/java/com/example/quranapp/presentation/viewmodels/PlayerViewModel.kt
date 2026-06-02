package com.example.quranapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.R
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.PlayerState
import com.example.quranapp.domain.error_classes.NextPreviousError
import com.example.quranapp.domain.use_case.player.CanSeekUseCase
import com.example.quranapp.domain.use_case.player.DisplayStateUseCase
import com.example.quranapp.domain.use_case.player.GetCurrentPositionUseCase
import com.example.quranapp.domain.use_case.player.PlayNextUseCase
import com.example.quranapp.domain.use_case.player.PlayPreviousUseCase
import com.example.quranapp.domain.use_case.player.PlayUseCase
import com.example.quranapp.domain.use_case.player.PlayerStateUseCase
import com.example.quranapp.domain.use_case.player.ReleaseUseCase
import com.example.quranapp.domain.use_case.player.SeekToUseCase
import com.example.quranapp.domain.use_case.player.SliderAnimationStateUseCase
import com.example.quranapp.domain.use_case.player.TogglePlayPauseUseCase
import com.example.quranapp.presentation.ui_events.NextPreviousUIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playUseCase: PlayUseCase,
    private val playerStateUseCase: PlayerStateUseCase,
    private val displayStateUseCase: DisplayStateUseCase,
    private val releaseUseCase: ReleaseUseCase,
    private val togglePlayPauseUseCase: TogglePlayPauseUseCase,
    private val playNextUseCase: PlayNextUseCase,
    private val playPreviousUseCase: PlayPreviousUseCase,
    private val getCurrentPositionUseCase: GetCurrentPositionUseCase,
    private val seekToUseCase: SeekToUseCase,
    private val canSeekUseCase: CanSeekUseCase,
    sliderAnimationStateUseCase: SliderAnimationStateUseCase
    ): ViewModel() {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _playerErrorState = MutableSharedFlow<NextPreviousUIEvents>()
    val playerErrorState = _playerErrorState.asSharedFlow()

    val sliderAnimationState = sliderAnimationStateUseCase()

    fun playerState(): StateFlow<PlayerState> {
        return playerStateUseCase()
    }

    fun displayState(): StateFlow<DisplayMetaData> {
        return displayStateUseCase()
    }

    fun play(surahNumber: Int, reciterUrlIdentifier: String) {
        playUseCase(surahNumber, reciterUrlIdentifier)
    }

    fun togglePlayPause(){
        togglePlayPauseUseCase()
    }

    fun playNext(){
        viewModelScope.launch {
            playNextUseCase().fold(
                onSuccess = {

                },
                onFailure = { t ->
                    when (t){
                        is NextPreviousError.NoActivePlaylist -> {
                            _playerErrorState.emit(NextPreviousUIEvents.Error(R.string.no_active_playlist_error_message))
                        }
                        is NextPreviousError.LastSurahReached -> {
                            _playerErrorState.emit(NextPreviousUIEvents.Error(R.string.now_playing_last_surah))
                        }
                    }
                }
            )
        }
    }

    fun playPrevious(){
        viewModelScope.launch {
            playPreviousUseCase().fold(
                onSuccess = {

                },
                onFailure = { t ->
                    when (t){
                        is NextPreviousError.NoActivePlaylist -> {
                            _playerErrorState.emit(NextPreviousUIEvents.Error(R.string.no_active_playlist_error_message))
                        }
                        is NextPreviousError.FirstSurahReached -> {
                            _playerErrorState.emit(NextPreviousUIEvents.Error(R.string.now_playing_first_surah))
                        }
                    }
                }
            )
        }
    }

    fun getCurrentPosition(): Long = getCurrentPositionUseCase()

    fun seekTo(fraction: Float){
        seekToUseCase(fraction)
    }

    fun canSeek(): Boolean = canSeekUseCase()

    fun releasePlayer(){
        releaseUseCase()
    }

}