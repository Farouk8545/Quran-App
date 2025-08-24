package com.example.quranapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quranapp.database.converters.Converters
import com.example.quranapp.database.models.DatabaseAudioSurah
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [DatabaseAudioSurah::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuranDataBase: RoomDatabase() {
    abstract fun quranDatabaseDao(): QuranDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuranDataBase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): QuranDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuranDataBase::class.java,
                    "quotes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}