package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
fun SurahCard(
    surah: String,
    reader: String,
    identifier: String,
    onPlayClicked: (String) -> Unit
){

    val apiViewModel: ApiViewModel = hiltViewModel()
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    val playingSurah by apiViewModel.surahState.collectAsState()
    val isPlayingFromPlayer by playerViewModel.isPlaying.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

    val context = LocalContext.current
    val readerImages = ReaderImages.getReaderImages(context)
    val isSelected = surah == if (selectedLanguage == "ar") playingSurah?.data?.name else playingSurah?.data?.englishName
    val isPlaying = isPlayingFromPlayer && isSelected

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp)
    ){
        Image(
            painter = painterResource(
                readerImages[reader] ?: R.drawable.place_holder
            ),
            contentDescription = "Reader Image",
            modifier = Modifier.height(96.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.size(16.dp))

        Column (
            modifier = Modifier.fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = surah,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.amiri_quran)),
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = reader,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        IconButton(
            modifier = Modifier.clip(CircleShape)
                .padding(end = 12.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (isSelected && identifier == playingSurah?.data?.edition?.identifier) AppPurple else Color.LightGray,
                contentColor = if (isSelected && identifier == playingSurah?.data?.edition?.identifier) Color.White else Color.Black,
            ),
            onClick = {
                if (identifier != playingSurah?.data?.edition?.identifier){
                    onPlayClicked(surah)
                }else{
                    if (!isSelected){
                        onPlayClicked(surah)
                    }else{
                        if (isPlaying){
                            playerViewModel.pause()
                        }else{
                            playerViewModel.resume()
                        }
                    }
                }

            }
        ) {
            Icon(
                painter = if (isPlaying && identifier == playingSurah?.data?.edition?.identifier) painterResource(R.drawable.pause)
                else painterResource(R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier.size(24.dp)
                    .padding(4.dp)
            )
        }
    }
}