package com.example.quranapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quranapp.database.models.DatabaseAudioSurah

@Dao
interface QuranDatabaseDao {

    @Insert
    suspend fun addSurahToLastListenedTo(surah: DatabaseAudioSurah)

    @Query("SELECT * FROM lastListenedToAudioSurah ORDER BY lastPlayedTime DESC LIMIT 10")
    suspend fun getAllFromLastListenedToSurah(): List<DatabaseAudioSurah>?

    @Query("SELECT EXISTS (SELECT 1 FROM lastListenedToAudioSurah WHERE surahNumber = :surahNumber AND identifier = :identifier)")
    suspend fun isSurahInLastListenedTo(surahNumber: Int, identifier: String): Boolean

    @Query("UPDATE lastListenedToAudioSurah SET lastPlayedTime = CURRENT_TIMESTAMP WHERE surahNumber = :surahNumber AND identifier = :identifier")
    suspend fun changeTimestamp(surahNumber: Int, identifier: String)

    @Query("DELETE FROM lastListenedToAudioSurah")
    suspend fun deleteAllFromLastListenedToSurah()

}