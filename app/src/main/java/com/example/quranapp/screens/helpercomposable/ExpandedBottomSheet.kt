package com.example.quranapp.screens.helpercomposable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedBottomSheet(
    onBackClicked : () -> Unit
    ){

    val playerViewModel: PlayerViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val surah by apiViewModel.surahState.collectAsState()
    val changeKey = surah?.data?.number ?: 0

    val context = LocalContext.current
    val readerImages = ReaderImages.getReaderImages(context)
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val readerName = if (selectedLanguage == "ar") surah?.data?.edition?.name else surah?.data?.edition?.englishName
    val surahName = if (selectedLanguage == "ar") surah?.data?.name else surah?.data?.englishName

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(top = 60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    onBackClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = stringResource(R.string.now_Playing),
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    //TODO: open menu
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.dots),
                    contentDescription = "Menu"
                )
            }
        }

        Spacer(Modifier.size(40.dp))

        Image(
            painter = painterResource(
                readerImages[readerName] ?: R.drawable.place_holder
            ),
            contentDescription = "Readers Image",
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = surahName ?: "Surah",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.amiri_quran)),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = readerName ?: "Reader",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.size(64.dp))

        key (changeKey){
            PlaySlider()
        }

        Spacer(Modifier.size(32.dp))

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    colors = IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Black
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
                    modifier = Modifier.size(48.dp)
                        .clip(CircleShape),
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
                    )
                }

                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    colors = IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Black
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