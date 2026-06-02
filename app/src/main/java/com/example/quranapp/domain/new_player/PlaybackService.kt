package com.example.quranapp.domain.new_player

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand

class PlaybackService: MediaSessionService() {

    private var mediaSession: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val rawPlayer = ExoPlayer.Builder(this).build()

        // Wrap the ExoPlayer instance with a ForwardingPlayer to intercept commands
        val forwardingPlayer = object : ForwardingPlayer(rawPlayer) {

            override fun seekToNext() {
                handleNextAyahIntercept()
            }

            override fun seekToPrevious() {
                handlePrevAyahIntercept()
            }
        }

        // Create the MediaSession with the wrapped player
        mediaSession = MediaSession.Builder(this, forwardingPlayer).build()
    }

    // Handle Next command
    private fun handleNextAyahIntercept() {
        mediaSession?.broadcastCustomCommand(
            SessionCommand("TRIGGER_NEXT_AYAH", Bundle.EMPTY),
            Bundle.EMPTY
        )
    }

    // Handle Prev command
    private fun handlePrevAyahIntercept() {
        mediaSession?.broadcastCustomCommand(
            SessionCommand("TRIGGER_PREV_AYAH", android.os.Bundle.EMPTY),
            Bundle.EMPTY
        )
    }

    // Returns the media session for the current service
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    // Releases the player and media session when the service is destroyed
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}