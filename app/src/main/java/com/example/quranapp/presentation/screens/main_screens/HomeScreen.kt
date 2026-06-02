package com.example.quranapp.presentation.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.data.local.database.DatabaseViewModel
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.data.local.models.FeaturedModel
import com.example.quranapp.domain.language.LanguageViewModel
import com.example.quranapp.presentation.navigation.MainNavigationViewModel
import com.example.quranapp.presentation.screens.helpercomposable.FeaturedReaders
import com.example.quranapp.presentation.screens.helpercomposable.LoadingScreen
import com.example.quranapp.presentation.screens.helpercomposable.SurahCard
import com.example.quranapp.presentation.viewmodels.ApiViewModel

@Composable
fun HomeScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val databaseViewModel: DatabaseViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()

    val lastListenedToSurah by databaseViewModel.lastListenedToSurah.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        databaseViewModel.getAllFromLastListenedToSurah()
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(32.dp)
    ){
        FeaturedReaders(
            listOf(
                FeaturedModel(context.getString(R.string.sheikh_alafasy), painterResource(R.drawable.mashary_rashed), "ar.alafasy"),
                FeaturedModel(context.getString(R.string.sheikh_maher), painterResource(R.drawable.maher_elmekly), "ar.mahermuaiqly"),
                FeaturedModel(context.getString(R.string.sheikh_abdulsamad), painterResource(R.drawable.abdulbaset_abdulsamad), "ar.abdulsamad"),
                FeaturedModel(context.getString(R.string.sheikh_husary), painterResource(R.drawable.husary), "ar.husary"),
                FeaturedModel(context.getString(R.string.sheikh_shaatree), painterResource(R.drawable.abu_bakr_ash_shaatree), "ar.shaatree")
            )
        ){
            //navViewModel.backStack.add(Screens.QuranPlaylistScreen())
        }

        Spacer(Modifier.size(16.dp))

        Text(
            text = stringResource(R.string.last_played),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.size(8.dp))

        LazyColumn (
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ){
            lastListenedToSurah?.let {
                if (it.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_last_played),
                                fontSize = 24.sp
                            )
                        }
                    }
                }else{
                    items (lastListenedToSurah ?: emptyList()){ surah ->
                        SurahCard(
                            surahName = if (selectedLanguage == "ar") surah.surahNameAr else surah.surahNameEn,
                            reciterMetaData = ReciterMetaData(
                                reciterId = surah.id.toInt(),
                                reciterNameAr = surah.readerNameAr,
                                reciterNameEn = surah.surahNameEn,
                                reciterUrlIdentifier = surah.identifier,
                                reciterUrlImage = ""
                            ),
                            isPlaying = false,
                            isSelected = false
                        ) {

                        }
                    }
                }
            } ?: run {
                item {
                    LoadingScreen()
                }
            }
        }
    }
}