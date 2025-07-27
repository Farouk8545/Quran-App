package com.example.quranapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.screens.helpercomposable.SurahCard

@Composable
fun QuranPlaylistScreen(
    reader: String,
    identifier: String
){

    val apiViewModel: ApiViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    val metadata = apiViewModel.metaData
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

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
                    reader,
                    identifier
                ) {
                    apiViewModel.getSurah(surah.number, identifier)
                }
            }
        }
    }
}