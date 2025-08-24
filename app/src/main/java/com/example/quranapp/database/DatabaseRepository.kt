package com.example.quranapp.database

import com.example.quranapp.database.models.DatabaseAudioSurah

class DatabaseRepository(private val quranDatabaseDao: QuranDatabaseDao){

    suspend fun addSurahToLastListenedTo(surah: DatabaseAudioSurah){
        quranDatabaseDao.addSurahToLastListenedTo(surah)
    }

    suspend fun getAllFromLastListenedToSurah(): List<DatabaseAudioSurah>?{
        return quranDatabaseDao.getAllFromLastListenedToSurah()
    }

    suspend fun isSurahInLastListenedTo(surahNumber: Int, identifier: String): Boolean{
        return quranDatabaseDao.isSurahInLastListenedTo(surahNumber, identifier)
    }

    suspend fun changeTimestamp(surahNumber: Int, identifier: String){
        quranDatabaseDao.changeTimestamp(surahNumber, identifier)
    }

    suspend fun deleteAllFromLastListenedToSurah(){
        quranDatabaseDao.deleteAllFromLastListenedToSurah()
    }

}