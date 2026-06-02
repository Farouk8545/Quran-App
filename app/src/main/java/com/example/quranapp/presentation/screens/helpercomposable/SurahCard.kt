package com.example.quranapp.presentation.screens.helpercomposable

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.quranapp.R
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun SurahCard(
    surahName: String,
    reciterMetaData: ReciterMetaData,
    isPlaying: Boolean,
    isSelected: Boolean,
    onPlayClicked: () -> Unit
){

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp)
    ){
        AsyncImage(
            model = reciterMetaData.reciterUrlImage,
            contentDescription = null,
            modifier = Modifier.height(96.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.place_holder),
            error = painterResource(R.drawable.place_holder)
        )

        Spacer(Modifier.size(16.dp))

        Column (
            modifier = Modifier.fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = surahName,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.amiri_quran)),
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = reciterMetaData.displayName,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        IconButton(
            modifier = Modifier.clip(CircleShape)
                .padding(end = 12.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = if (isSelected) AppPurple else Color.LightGray,
                contentColor = if (isSelected) Color.White else Color.Black,
            ),
            onClick = {
                onPlayClicked()
            }
        ) {
            Icon(
                painter = if (isPlaying && isSelected) painterResource(R.drawable.pause)
                else painterResource(R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier.size(24.dp)
                    .padding(4.dp)
            )
        }
    }
}