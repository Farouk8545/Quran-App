package com.example.quranapp.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.media3.common.Player
import javax.inject.Inject

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val playerManager = AudioPlayerService.instance?.playerManager

        when (intent.action) {
            ACTION_NEXT -> {
                ApiViewModelBridge.apiViewModel?.playNext()
                println(ApiViewModelBridge.apiViewModel == null)
            }
            ACTION_PLAY -> {
                if (playerManager?.player?.playbackState == Player.STATE_ENDED){
                    playerManager.player.seekTo(0, 0L)
                }
                playerManager?.player?.play()
            }
            ACTION_PAUSE -> {
                playerManager?.player?.pause()
            }
        }
    }

    companion object {
        const val ACTION_NEXT = "com.example.quranapp.ACTION_NEXT"
        const val ACTION_PLAY = "com.example.quranapp.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.quranapp.ACTION_PAUSE"
    }
}