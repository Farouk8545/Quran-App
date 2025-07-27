package com.example.quranapp.player

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.quranapp.api.models.surah.AyahModel
import com.example.quranapp.models.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val playerManager: PlayerManager,
    @ApplicationContext val context: Context
    ): ViewModel() {

    val player = playerManager.player

    var sliderModel by mutableStateOf<SliderModel>(SliderModel(0, 0))

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _surahEnded = MutableSharedFlow<Unit>()
    val surahEnded = _surahEnded.asSharedFlow()

    private val _isExpanded = MutableSharedFlow<Boolean>()
    val isExpanded = _isExpanded.asSharedFlow()

    var userPress = false

    init {
        player.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED){
                    _isPlaying.value = false
                    if (player.currentMediaItemIndex == (player.mediaItemCount - 1) && !userPress){
                        viewModelScope.launch {
                            _surahEnded.emit(Unit)
                        }
                    }
                }
                if (playbackState == Player.STATE_READY){
                    sliderModel = SliderModel(player.currentMediaItemIndex, player.duration)
                    userPress = false
                }
            }

            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                _isPlaying.value = isPlayingNow
                AudioPlayerService.instance?.updateNotification(
                    playerManager.surahName ?: "Surah",
                    playerManager.readerName ?: "Reader",
                    isPlayingNow
                )
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                sliderModel = SliderModel(player.currentMediaItemIndex, player.duration)
            }
        })
    }

    fun play(surah: List<AyahModel>){
        player.clearMediaItems()
        val mediaItems = surah.map { ayah ->
            MediaItem.fromUri(ayah.audio)
        }
        player.addMediaItems(mediaItems)
        player.prepare()
        val intent = Intent(context, AudioPlayerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        player.playWhenReady = true
    }

    fun pause(){
        player.pause()
    }

    fun resume(){
        if (player.playbackState == Player.STATE_ENDED){
            player.seekTo(0, 0L)
        }
        player.play()
    }

    fun seekTo(ayahIndex: Int){
        player.seekTo(ayahIndex, 0L)
    }

    fun changeIsExpanded(state: Boolean){
        viewModelScope.launch {
            _isExpanded.emit(state)
        }
    }

}