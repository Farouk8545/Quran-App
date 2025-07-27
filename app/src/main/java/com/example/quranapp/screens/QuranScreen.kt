package com.example.quranapp.screens

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
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.TextSurahCard
import com.example.quranapp.screens.model.Screens

@Composable
fun QuranScreen(){

    val apiViewModel: ApiViewModel = hiltViewModel()
    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    val metaData = apiViewModel.metaData
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    LazyColumn (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ){
        items (metaData.data.surahs.references ){
            TextSurahCard(
                surah = if (selectedLanguage == "ar") it.name else it.englishName,
                number = it.number
            ) {
                navViewModel.backStack.add(Screens.TextQuranScreen(it.number))
            }
        }
    }
}