package com.example.quranapp.screens.helpercomposable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.R
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.player.PlayerViewModel
import com.example.quranapp.ui.theme.AppPurple
import com.example.quranapp.util.ReaderImages

@Composable
fun CollapsedBottomSheet(){
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()

    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val surah by apiViewModel.surahState.collectAsState()
    val languageViewModel: LanguageViewModel = viewModel()

    val context = LocalContext.current
    val readerImages = ReaderImages.getReaderImages(context)
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val readerName = if (selectedLanguage == "ar") surah?.data?.edition?.name else surah?.data?.edition?.englishName
    val surahName = if (selectedLanguage == "ar") surah?.data?.name else surah?.data?.englishName


    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Row {
            Image(
                painter = painterResource(
                    readerImages[readerName] ?: R.drawable.place_holder
                ),
                contentDescription = "Reader Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(100.dp)
                    .width(100.dp)
            )

            Column (
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ){
                Text(
                    surahName ?: "Surah",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    color = Color.Black
                )

                Text(
                    readerName ?: "Reader",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.widthIn(max = 130.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        modifier = Modifier.clip(CircleShape)
                            .padding(end = 12.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            surah?.data?.number.let {
                                if(it != null && it > 1){
                                    playerViewModel.userPress = true
                                    playerViewModel.pause()
                                    apiViewModel.playPrevious()
                                }else{
                                    Toast.makeText(context, context.getString(R.string.now_playing_first_surah),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.previous_button),
                            contentDescription = "Previous",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        modifier = Modifier.clip(CircleShape)
                            .padding(end = 12.dp),
                        colors = IconButtonColors(
                            containerColor = AppPurple,
                            contentColor = Color.White,
                            disabledContainerColor = AppPurple,
                            disabledContentColor = Color.White
                        ),
                        onClick = {
                            if (playerViewModel.isPlaying.value){
                                playerViewModel.pause()
                            }else{
                                playerViewModel.resume()
                            }
                        }
                    ) {
                        Icon(
                            painter = if (isPlaying) painterResource(R.drawable.pause)
                            else painterResource(R.drawable.play),
                            contentDescription = "Play",
                            modifier = Modifier.size(24.dp)
                                .padding(4.dp)
                        )
                    }

                    IconButton(
                        modifier = Modifier.clip(CircleShape)
                            .padding(end = 8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            surah?.data?.number.let {
                                if(it != null && it < 114){
                                    playerViewModel.userPress = true
                                    playerViewModel.pause()
                                    apiViewModel.playNext()
                                }else{
                                    Toast.makeText(context, context.getString(R.string.now_playing_last_surah),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.next_button),
                            contentDescription = "next",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

        }
    }
}