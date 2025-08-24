package com.example.quranapp.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "lastListenedToAudioSurah")
data class DatabaseAudioSurah(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val surahNameAr: String,
    val surahNameEn: String,
    val surahNumber: Int,
    val readerNameAr: String,
    val readerNameEn: String,
    val identifier: String,
    val lastPlayedTime: Timestamp = Timestamp(System.currentTimeMillis())
)
