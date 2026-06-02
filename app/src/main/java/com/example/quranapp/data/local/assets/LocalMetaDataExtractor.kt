package com.example.quranapp.data.local.assets

import android.content.Context
import android.util.Log
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.data.local.dto.reciters.RecitersMetaDataResponse
import com.example.quranapp.data.local.dto.surahs.SurahMetaData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMetaDataExtractor @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    // Creates a json object for deserialization
    private val json = Json {ignoreUnknownKeys = true}

    // Deserialize the json files and obtain the meta data
    private val recitersMetaDataCache by lazy {
        val jsonString = context.assets.open("reciters_meta_data.json").bufferedReader().use { it.readText() }
        json.decodeFromString<RecitersMetaDataResponse>(jsonString).recitersMetaData
    }
    private val surahMetaDataCache by lazy {
        val jsonString = context.assets.open("surah_meta_data.json").bufferedReader().use { it.readText() }
        json.decodeFromString<List<SurahMetaData>>(jsonString)
    }

    // Returns the meta data for reciters
    fun getRecitersMetaData(): List<ReciterMetaData> {
        return recitersMetaDataCache
    }

    // Returns the meta data for the specified reciter
    fun getSingleReciterMetaData(reciterUrlIdentifier: String): ReciterMetaData {
        return recitersMetaDataCache.find { it.reciterUrlIdentifier == reciterUrlIdentifier }
            ?: throw IllegalArgumentException("Reciters not found")
    }

    // Returns the meta data for surahs
    fun getSurahMetaData(): List<SurahMetaData> {
        return surahMetaDataCache
    }

    // Returns the meta data for the specified surah
    fun getSingleSurahMetaData(surahNumber: Int): SurahMetaData {
        return surahMetaDataCache[surahNumber - 1]
    }

    // Returns the display meta data for the specified surah and reciter
    fun getDisplayMetaData(surahNumber: Int, reciterUrlIdentifier: String): DisplayMetaData {
        val surahName = surahMetaDataCache.find { it.number == surahNumber }?.displayName ?: "Known"
        val reciterName = recitersMetaDataCache.find { it.reciterUrlIdentifier == reciterUrlIdentifier }?.displayName ?: "Known"
        val reciterImageUrl = recitersMetaDataCache.find { it.reciterUrlIdentifier == reciterUrlIdentifier }?.reciterUrlImage ?: "Known"
        return DisplayMetaData(
            surahName = surahName,
            surahNumber = surahNumber,
            reciterName = reciterName,
            reciterImageUrl = reciterImageUrl,
            reciterUrlIdentifier = reciterUrlIdentifier
        )
    }


}