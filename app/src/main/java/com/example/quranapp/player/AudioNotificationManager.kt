package com.example.quranapp.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.quranapp.R
import com.example.quranapp.util.ReaderImages

object AudioNotificationManager {
    const val NOTIFICATION_ID = 1001
    const val CHANNEL_ID = "quran_audio_channel"

    fun buildNotification(
        context: Context,
        mediaSession: android.support.v4.media.session.MediaSessionCompat,
        title: String,
        readerName: String,
        isPlaying: Boolean
    ): Notification{
        val playIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, NotificationActionReceiver::class.java).apply {
                action = NotificationActionReceiver.ACTION_PLAY
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pauseIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, NotificationActionReceiver::class.java).apply {
                action = NotificationActionReceiver.ACTION_PAUSE
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val nextIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, NotificationActionReceiver::class.java).apply {
                action = NotificationActionReceiver.ACTION_NEXT
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(readerName)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, ReaderImages.getReaderImages(context)[readerName] ?: R.drawable.notification_background))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                if (isPlaying)
                    NotificationCompat.Action(R.drawable.notification_pause, "Pause", pauseIntent)
                else
                    NotificationCompat.Action(R.drawable.notification_play, "Play", playIntent)
            )
            .addAction(NotificationCompat.Action(R.drawable.notification_next, "Next", nextIntent))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0, 1)
            )
            .setOngoing(isPlaying)
            .setOnlyAlertOnce(true)
            .build()
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Quran Audio",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Audio playback controls for Quran app"
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}