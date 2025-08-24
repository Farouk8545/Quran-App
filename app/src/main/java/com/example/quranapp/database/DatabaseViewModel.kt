package com.example.quranapp.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.database.models.DatabaseAudioSurah
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    val repository: DatabaseRepository

    init {
        val quranDatabaseDao = QuranDataBase.getDatabase(context = context).quranDatabaseDao()
        repository = DatabaseRepository(quranDatabaseDao)
    }

    private val _lastListenedToSurah = MutableStateFlow<List<DatabaseAudioSurah>?>(null)
    var lastListenedToSurah = _lastListenedToSurah.asStateFlow()

    var isSurahInLastListenedTo: Boolean? = null

    fun addSurahToLastListenedTo(surah: DatabaseAudioSurah){
        viewModelScope.launch {
            repository.addSurahToLastListenedTo(surah)
        }
    }

    fun getAllFromLastListenedToSurah(){
        viewModelScope.launch {
            _lastListenedToSurah.value = repository.getAllFromLastListenedToSurah()
        }
    }

    suspend fun isSurahInLastListenedTo(surahNumber: Int, identifier: String): Boolean{
        return repository.isSurahInLastListenedTo(surahNumber, identifier)
    }

    fun changeTimestamp(surahNumber: Int, identifier: String){
        viewModelScope.launch {
            repository.changeTimestamp(surahNumber, identifier)
        }
    }

    fun deleteAllFromLastListenedToSurah(){
        viewModelScope.launch {
            repository.deleteAllFromLastListenedToSurah()
        }
    }

}