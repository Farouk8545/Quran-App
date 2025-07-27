package com.example.quranapp.player

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.quranapp.api.viewmodel.ApiViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext context: Context
){
    val player = ExoPlayer.Builder(context).build()
    var surahName: String? = null
    var readerName: String? = null
}

object ApiViewModelBridge{
    var apiViewModel: ApiViewModel? = null
}