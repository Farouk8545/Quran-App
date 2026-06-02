package com.example.quranapp.presentation.screens.helpercomposable

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.quranapp.R
import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun FullPlayerDisplay(
    reciterName: String,
    reciterImageUrl: String,
    surahName: String,
    isPlaying: Boolean,
    playbackState: Int,
    sliderAnimationSpecs: SliderAnimationSpecs,
    currentPosition: () -> Long,
    onBackClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    seekTo: (fraction: Float) -> Unit,
    canSeek: () -> Boolean
){

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

        AsyncImage(
            model = reciterImageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.place_holder),
            error = painterResource(R.drawable.place_holder)
        )

        Text(
            text = surahName,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.amiri_quran)),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = reciterName,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.size(64.dp))

        PlaySlider(
            isPlaying = isPlaying,
            playbackState = playbackState,
            sliderAnimationSpecs = sliderAnimationSpecs,
            currentPosition = currentPosition,
            seekTo = seekTo,
            canSeek = canSeek
        )

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
                        onPreviousClicked()
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
                        onPlayClicked()
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
                        onNextClicked()
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