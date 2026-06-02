package com.example.quranapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.R
import com.example.quranapp.domain.use_case.reciter.GetRecitersMetaDataUseCase
import com.example.quranapp.presentation.ui_events.PlaylistScreenUIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor(
    private val getMetaDataUseCase: GetRecitersMetaDataUseCase
): ViewModel() {

    private val _uIEvents = MutableStateFlow<PlaylistScreenUIEvents>(PlaylistScreenUIEvents.Loading)
    val uIEvents = _uIEvents.asStateFlow()

    init {
        getMetaData()
    }

    fun getMetaData(){
        viewModelScope.launch {
            try {
                val metaData = getMetaDataUseCase()
                _uIEvents.value = PlaylistScreenUIEvents.Success(metaData)
            }catch (e: IllegalArgumentException){
                Log.d("Inside the playlist viewmodel", e.message.toString())
                _uIEvents.value = PlaylistScreenUIEvents.Error(R.string.meta_data_error_loading_message)
            }
        }
    }
}