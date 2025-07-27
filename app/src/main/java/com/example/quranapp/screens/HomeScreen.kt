package com.example.quranapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.models.FeaturedModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.FeaturedReaders
import com.example.quranapp.screens.model.Screens

@Composable
fun HomeScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()

    val context = LocalContext.current

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
            navViewModel.backStack.add(Screens.QuranPlaylistScreen(it.name, it.identifier))
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
        ){

        }
    }
}