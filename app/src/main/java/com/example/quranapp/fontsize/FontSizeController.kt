package com.example.quranapp.fontsize

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object FontSizeController {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("font_settings")

    private val FONT_SIZE = floatPreferencesKey("font_size")
    var fontSize = mutableFloatStateOf(16f)

    suspend fun saveFont(context: Context, fontSize: Float){
        context.dataStore.edit { fontSettings ->
            fontSettings[FONT_SIZE] = fontSize
        }
        FontSizeController.fontSize.floatValue = fontSize
    }

    suspend fun getFont(context: Context){
        fontSize.floatValue = context.dataStore.data.first()[FONT_SIZE] ?: 16f
    }
}