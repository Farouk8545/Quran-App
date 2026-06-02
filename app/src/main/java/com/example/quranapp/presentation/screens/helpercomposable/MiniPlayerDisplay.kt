package com.example.quranapp.presentation.screens.helpercomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.quranapp.R
import com.example.quranapp.data.local.dto.player.DisplayMetaData
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun MiniPlayerDisplay(
    modifier: Modifier,
    displayMetaData: DisplayMetaData,
    isPlaying: Boolean,
    onPlayClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onPlayerClicked: () -> Unit,
    onCloseClicked: () -> Unit
){

    Box (
        modifier = modifier.clickable(
                onClick = {
                    onPlayerClicked()
                }
            )
    ){
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                .clickable(
                    onClick = onCloseClicked,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Screen",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Row {
            AsyncImage(
                model = displayMetaData.reciterImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(R.drawable.place_holder),
                error = painterResource(R.drawable.place_holder)
            )

            Column (
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ){
                Text(
                    displayMetaData.surahName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    color = Color.Black
                )

                Text(
                    displayMetaData.reciterName,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.widthIn(max = 130.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    modifier = Modifier.align(Alignment.Bottom)
                        .padding(bottom = 8.dp)
                ) {
                    IconButton(
                        modifier = Modifier.clip(CircleShape)
                            .padding(end = 12.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
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
                        modifier = Modifier.clip(CircleShape)
                            .padding(end = 12.dp),
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
}