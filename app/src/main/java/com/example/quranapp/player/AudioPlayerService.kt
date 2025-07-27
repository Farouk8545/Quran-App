package com.example.quranapp.player

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import androidx.media3.session.MediaSession
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class AudioPlayerService: Service() {

    @Inject lateinit var playerManager: PlayerManager
    private lateinit var mediaSession: MediaSession

    companion object {
        var instance: AudioPlayerService? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        val player = playerManager.player

        mediaSession = MediaSession.Builder(this, player)
            .setId("QuranMediaSession")
            .build()

        val mediaSessionCompat = android.support.v4.media.session.MediaSessionCompat(this, "CompatSession")

        AudioNotificationManager.createNotificationChannel(this)
        val notification = AudioNotificationManager.buildNotification(
            context = this,
            mediaSession = mediaSessionCompat,
            title = playerManager.surahName.toString(),
            readerName = playerManager.readerName.toString(),
            isPlaying = player.isPlaying
        )

        startForeground(AudioNotificationManager.NOTIFICATION_ID, notification)
    }

    fun updateNotification(title: String, readerName: String, isPlaying: Boolean) {
        val mediaSessionCompat = android.support.v4.media.session.MediaSessionCompat(this, "CompatSession")
        val notification = AudioNotificationManager.buildNotification(
            context = this,
            mediaSession = mediaSessionCompat,
            title = title,
            readerName = readerName,
            isPlaying = isPlaying
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(AudioNotificationManager.NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}