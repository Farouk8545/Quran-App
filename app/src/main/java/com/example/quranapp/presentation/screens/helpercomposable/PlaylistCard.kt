package com.example.quranapp.presentation.screens.helpercomposable

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.quranapp.R
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlaylistCard(
    metaData: ReciterMetaData,
    onClick: () -> Unit
){

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            )
    ){
        AsyncImage(
            model = metaData.reciterUrlImage,
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
                text = metaData.displayName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = stringResource(R.string.audio_count),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}