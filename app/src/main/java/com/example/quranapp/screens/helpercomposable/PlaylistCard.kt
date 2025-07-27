package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.R
import com.example.quranapp.util.ReaderImages

@Composable
fun PlaylistCard(
    reader: String,
    onClick: () -> Unit
){
    val context = LocalContext.current
    val readerImages = remember { ReaderImages.getReaderImages(context) }

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            )
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
                text = reader,
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