package com.example.quranapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.LoadingScreen
import com.example.quranapp.screens.helpercomposable.PlaylistCard
import com.example.quranapp.screens.model.Screens

@Composable
fun PlaylistsScreen(){

    val apiViewModel: ApiViewModel = hiltViewModel()
    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    val edition by apiViewModel.edition.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    LaunchedEffect(Unit) {
        apiViewModel.getEditions()
    }

    if (apiViewModel.isEditionLoading){
        LoadingScreen()
    }else{
        Box (
            modifier = Modifier.padding(24.dp)
        ){
            LazyColumn {
                items(edition?.data ?: emptyList()) { edition ->
                    val readerName = if (selectedLanguage == "ar") edition.name else edition.englishName
                    PlaylistCard(readerName) {
                        navViewModel.backStack.add(Screens.QuranPlaylistScreen(readerName, edition.identifier))
                    }
                }
            }
        }
    }
}