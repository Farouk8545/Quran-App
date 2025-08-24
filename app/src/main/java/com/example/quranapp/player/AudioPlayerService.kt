package com.example.quranapp.player

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class AudioPlayerService : Service() {

    @Inject lateinit var playerManager: PlayerManager
    private lateinit var mediaSession: MediaSession

    companion object {
        var instance: AudioPlayerService? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        mediaSession = MediaSession.Builder(this, playerManager.player)
            .setId("QuranMediaSession")
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // ✅ Ensure the channel is created
        AudioNotificationManager.createNotificationChannel(this)

        // ✅ Use fallback values in case surahName/readerName are not yet set
        val mediaSessionCompat = android.support.v4.media.session.MediaSessionCompat(this, "CompatSession")
        val notification = AudioNotificationManager.buildNotification(
            context = this,
            mediaSession = mediaSessionCompat,
            title = playerManager.surahName ?: "Quran",
            readerName = playerManager.readerName ?: "Reader",
            isPlaying = false // You can update this later
        )

        // ✅ Must call startForeground here to avoid crash
        startForeground(AudioNotificationManager.NOTIFICATION_ID, notification)

        return START_STICKY
    }

    fun createNotification(title: String, readerName: String, isPlaying: Boolean) {
        AudioNotificationManager.createNotificationChannel(this)

        val mediaSessionCompat = android.support.v4.media.session.MediaSessionCompat(this, "CompatSession")
        val notification = AudioNotificationManager.buildNotification(
            context = this,
            mediaSession = mediaSessionCompat,
            title = title,
            readerName = readerName,
            isPlaying = isPlaying
        )

        // Update notification
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
