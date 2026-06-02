package com.example.quranapp.data.local.repository

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.quranapp.data.local.assets.LocalMetaDataExtractor
import com.example.quranapp.data.local.assets.UrlBuilder
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.domain.error_classes.NextPreviousError
import com.example.quranapp.domain.new_player.MediaController
import com.example.quranapp.domain.repository.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val mediaController: MediaController,
    private val extractor: LocalMetaDataExtractor,
    private val urlBuilder: UrlBuilder
): PlayerRepository {

    // Exposing the playerState (from MediaController)
    override val playerState = mediaController.playerState

    // Exposing the sliderAnimationState (from MediaController)
    override val sliderAnimationState: StateFlow<SliderAnimationSpecs> = mediaController.sliderAnimationState

    // Exposing the Display data (Audio name, Reciter name, etc)
    private val _displayState = MutableStateFlow<DisplayMetaData>(DisplayMetaData())
    override val displayState: StateFlow<DisplayMetaData> = _displayState.asStateFlow()

    // Listening to the button clicks
    private val buttonsAction = mediaController.buttonsAction
    private val repositoryScope = CoroutineScope(Dispatchers.Main)

    init {
        repositoryScope.launch {
            buttonsAction.collect { action ->
                when (action){
                    MediaController.ButtonsAction.NextButtonClicked -> playNext()
                    MediaController.ButtonsAction.PrevButtonClicked -> playPrevious()
                }
            }
        }
    }

    // Caching the current audio and reciter
    var surahNumberCache: Int? = null
    var reciterUrlIdentifierCache: String? = null

    // Plays the specified audio for the specified reciter
    override fun play(surahNumber: Int, reciterUrlIdentifier: String) {

        // Extracts meta data
        val reciterMetaData = extractor.getSingleReciterMetaData(reciterUrlIdentifier)
        val surahMetaData = extractor.getSingleSurahMetaData(surahNumber)

        // Builds the urls for the desired audio
        val urls = urlBuilder.buildUrl(surahNumber, reciterUrlIdentifier, 1, surahMetaData.numberOfAyahs)

        // Created a media item object for the desired audio
        val mediaItems = urls.map { url ->
            MediaItem.Builder()
                .setMediaId(surahMetaData.displayName)
                .setUri(url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(surahMetaData.displayName)
                        .setArtist(reciterMetaData.displayName)
                        .setArtworkUri(reciterMetaData.reciterUrlImage.toUri())
                        .build()
                )
                .build()
        }

        // Pass the media item back to MediaController to play the audio
        mediaController.play(mediaItems)

        // Update the states and cache data
        updateDisplayState(surahNumber, reciterUrlIdentifier)
        surahNumberCache = surahNumber
        reciterUrlIdentifierCache = reciterUrlIdentifier
    }

    // Toggles the play state of the audio
    override fun togglePlayPause() {
        mediaController.togglePlayPause()
    }

    // Plays the next audio in the playlist
    override fun playNext(): Result<Unit> = runCatching {
        if (surahNumberCache == null || reciterUrlIdentifierCache == null) {
            throw NextPreviousError.NoActivePlaylist
        }

        if (surahNumberCache!! >= 114){
            throw NextPreviousError.LastSurahReached
        }else {
            play(surahNumberCache!! + 1, reciterUrlIdentifierCache!!)
        }
    }

    // Plays the previous audio in the playlist
    override fun playPrevious(): Result<Unit> = runCatching {
        if (surahNumberCache == null || reciterUrlIdentifierCache == null) {
            throw NextPreviousError.NoActivePlaylist
        }

        if (surahNumberCache!! <= 1){
            throw NextPreviousError.FirstSurahReached
        }else {
            play(surahNumberCache!! - 1, reciterUrlIdentifierCache!!)
        }
    }

    // Returns the current position of the audio in millis
    override fun getCurrentPosition(): Long {
        return mediaController.getCurrentPosition()
    }

    // Jumps to the passed fraction of the audio (Whole Surah, not just the Ayah)
    override fun seekTo(fraction: Float) {
        mediaController.seekTo(fraction)
    }

    // Checks if the browser instance is initialized before allowing the user to jump forward
    override fun canSeek(): Boolean {
        return mediaController.canSeek()
    }

    // Release the current player instance
    override fun release() {
        mediaController.release()
        _displayState.value = DisplayMetaData()
    }

    // Updates the display state
    private fun updateDisplayState(surahNumber: Int, reciterUrlIdentifier: String) {
        _displayState.update { extractor.getDisplayMetaData(surahNumber, reciterUrlIdentifier) }
    }
}