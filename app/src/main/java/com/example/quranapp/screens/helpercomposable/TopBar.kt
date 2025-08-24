package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.model.Screens
import com.example.quranapp.ui.theme.WarmGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(screenName: String){
    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()

    val textSurah by apiViewModel.textSurah.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    println(textSurah?.type)

    val typeMap = mapOf<String, String>(
        "meccan" to "مكّية",
        "medinan" to "مدنية"
    )

    TopAppBar (
        title = {
            if (navViewModel.backStack.lastOrNull() !is Screens.TextQuranScreen){
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = screenName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                }
            }else{
                Row (
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = textSurah?.name ?: "Surah",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.amiri_quran))
                    )

                    Text(
                        text =
                            (if (selectedLanguage == "ar") typeMap[textSurah?.type ?: stringResource(R.string.revelation_type)]
                             else textSurah?.type ?: stringResource(R.string.revelation_type))
                             ?: stringResource(R.string.revelation_type),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.amiri_quran))
                    )
                }
            }
        },
        navigationIcon = {
            if (
                navViewModel.backStack.lastOrNull() !is Screens.HomeScreen &&
                navViewModel.backStack.lastOrNull() !is Screens.QuranScreen &&
                navViewModel.backStack.lastOrNull() !is Screens.PlaylistsScreen &&
                navViewModel.backStack.lastOrNull() !is Screens.DivisionsScreen
            ){
                IconButton(
                    onClick = {
                        navViewModel.backStack.removeLastOrNull()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (navViewModel.backStack.lastOrNull() !is Screens.TextQuranScreen &&
                navViewModel.backStack.lastOrNull() !is Screens.SettingsScreen){
                IconButton(
                    onClick = {
                        navViewModel.backStack.add(Screens.SettingsScreen)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        },
        modifier = Modifier.background(
            if (navViewModel.backStack.lastOrNull() is Screens.TextQuranScreen) WarmGray else Color.White
        )
    )
}