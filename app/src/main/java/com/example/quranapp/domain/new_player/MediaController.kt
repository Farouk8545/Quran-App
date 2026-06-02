package com.example.quranapp.domain.new_player

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.data.local.dto.player.PlayerState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MediaController @Inject constructor(@ApplicationContext private val context: Context) {

    // creates a browser instance for the current player
    private var browser: MediaBrowser? = null

    // exposes the player state
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState = _playerState.asStateFlow()

    // Exposes the current position for animating the progress slider
    private val _sliderAnimationState = MutableStateFlow(SliderAnimationSpecs())
    val sliderAnimationState = _sliderAnimationState.asStateFlow()

    // Exposes the clicks of the next and previous buttons
    private val _buttonsAction = MutableSharedFlow<ButtonsAction>(extraBufferCapacity = 1)
    val buttonsAction = _buttonsAction.asSharedFlow()

    // Interface defining objects for the buttons clicks
    sealed interface ButtonsAction {
        object NextButtonClicked: ButtonsAction
        object PrevButtonClicked: ButtonsAction
    }

    // Listener for state changes of the player
    private val listener = object: Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _playerState.update { it.copy(isPlaying = isPlaying) }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _playerState.update { it.copy(playbackState = playbackState) }

            if (playbackState == Player.STATE_READY && browser?.currentMediaItemIndex == 0) {
                _sliderAnimationState.update { it.copy(
                    duration = browser?.duration ?: C.TIME_UNSET,
                    startValue = 0f,
                    targetValue = (1f / (browser?.mediaItemCount?.toFloat() ?: 1f))
                ) }
            }
        }

        override fun onEvents(player: Player, events: Player.Events) {
            if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)){
                _sliderAnimationState.update { it.copy(
                    duration = player.duration,
                    startValue = (player.currentMediaItemIndex.toFloat() / player.mediaItemCount.toFloat()),
                    targetValue = ((player.currentMediaItemIndex + 1).toFloat() / player.mediaItemCount.toFloat())
                ) }
            }
        }
    }

    // Listener for commands from the service
    private val browserListener = object : MediaBrowser.Listener {
        override fun onCustomCommand(
            controller: androidx.media3.session.MediaController,
            command: androidx.media3.session.SessionCommand,
            args: android.os.Bundle
        ): com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.SessionResult> {

            when (command.customAction) {
                "TRIGGER_NEXT_AYAH" -> {
                    _buttonsAction.tryEmit(ButtonsAction.NextButtonClicked)
                }
                "TRIGGER_PREV_AYAH" -> {
                    _buttonsAction.tryEmit(ButtonsAction.PrevButtonClicked)
                }
            }

            return com.google.common.util.concurrent.Futures.immediateFuture(
                androidx.media3.session.SessionResult(androidx.media3.session.SessionResult.RESULT_SUCCESS)
            )
        }
    }

    // Connects to the service
    fun connect(onReady: () -> Unit){
        if (browser != null){
            onReady()
            return
        }

        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        val controllerFuture = MediaBrowser.Builder(context, sessionToken)
            .setListener(browserListener)
            .buildAsync()

        controllerFuture.addListener({
            val initializedBrowser = controllerFuture.get()
            browser = initializedBrowser

            initializedBrowser.addListener(listener)

            _playerState.update {
                PlayerState(
                    isPlaying = initializedBrowser.isPlaying,
                    playbackState = initializedBrowser.playbackState,
                    isReady = true
                )
            }

            onReady()
        }, ContextCompat.getMainExecutor(context))
    }

    // Plays the passed list of URLs
    fun play(mediaItems: List<MediaItem>){
        if (browser == null){
            connect{
                executePlayback(mediaItems)
            }
        }else {
            executePlayback(mediaItems)
        }
    }

    // Executes the code for playback start
    private fun executePlayback(mediaItems: List<MediaItem>){
        browser?.clearMediaItems()
        browser?.setMediaItems(mediaItems)
        browser?.playWhenReady = true
        browser?.prepare()
    }

    // Toggles Play state of the player
    fun togglePlayPause(){
        if (browser?.isPlaying == true){
            browser?.pause()
        }else{
            browser?.play()
        }
    }


    // Returns the current position of the audio in millis
    fun getCurrentPosition(): Long {
        return browser?.currentPosition ?: 0L
    }

    // Jumps to the passed fraction of the audio (Whole Surah, not just the Ayah)
    fun seekTo(fraction: Float) {
        val currentAyahIndex = (fraction * (browser?.mediaItemCount?.toFloat() ?: 1f)).toInt()
        browser?.seekTo(currentAyahIndex, 0L)
    }

    // Checks if the browser instance is initialized before allowing the user to jump forward
    fun canSeek(): Boolean {
        return browser != null
    }

    // Release the current player instance
    fun release(){
        browser?.let { player ->
            player.stop()
            player.clearMediaItems()
        }
        browser?.removeListener(listener)
        browser?.release()
        browser = null
        _playerState.value = PlayerState()
    }

}