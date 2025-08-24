package com.example.quranapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.database.DatabaseViewModel
import com.example.quranapp.database.models.DatabaseAudioSurah
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.screens.helpercomposable.SurahCard
import kotlinx.coroutines.launch

@Composable
fun QuranPlaylistScreen(
    readerNameAr: String,
    readerNameEn: String,
    identifier: String
){

    val apiViewModel: ApiViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()
    val databaseViewModel: DatabaseViewModel = hiltViewModel()

    val metadata = apiViewModel.metaData
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val scope = rememberCoroutineScope()

    Box (
        modifier = Modifier.fillMaxSize()
            .padding(24.dp)
    ){
        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(metadata.data.surahs.references ){surah ->
                SurahCard(
                    if (selectedLanguage == "ar") surah.name else surah.englishName,
                    if (selectedLanguage == "ar") readerNameAr else readerNameEn,
                    identifier
                ) {
                    apiViewModel.getSurah(surah.number, identifier)
                    scope.launch {
                        val exist = databaseViewModel.isSurahInLastListenedTo(surah.number, identifier)
                        if (exist == true){
                            databaseViewModel.changeTimestamp(surah.number, identifier)
                            databaseViewModel.getAllFromLastListenedToSurah()
                        }else{
                            databaseViewModel.addSurahToLastListenedTo(
                                DatabaseAudioSurah(
                                    surahNameAr = surah.name,
                                    surahNameEn = surah.englishName,
                                    surahNumber = surah.number,
                                    readerNameAr = readerNameAr,
                                    readerNameEn = readerNameEn,
                                    identifier = identifier
                                )
                            )
                            databaseViewModel.getAllFromLastListenedToSurah()
                        }
                    }
                }
            }
        }
    }
}