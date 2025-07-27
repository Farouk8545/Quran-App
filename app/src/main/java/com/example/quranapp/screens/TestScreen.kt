package com.example.quranapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.player.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen() {
    val context = LocalContext.current
    val apiViewModel: ApiViewModel = hiltViewModel()
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val player = remember {
        ExoPlayer.Builder(context).build()
    }
    LaunchedEffect(Unit) {
        apiViewModel.getSurah(2, "ar.alafasy")
    }



    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 80.dp,
        sheetSwipeEnabled = true,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Player Controls", color = Color.White)
                Text("Extra Info", color = Color.Gray)
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content")
        }
    }

}