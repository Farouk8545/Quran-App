package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.R
import com.example.quranapp.ui.theme.AppPurple
import com.example.quranapp.ui.theme.LavenderBackground

@Composable
fun TextSurahCard(
    surah: String,
    number: Int,
    onClick: () -> Unit
){
    Card (
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row (
            modifier = Modifier.fillMaxSize()
                .clickable(
                    onClick = {
                        onClick()
                    }
                )
        ){
            Box(
                modifier = Modifier.padding(12.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LavenderBackground)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ){
                Text(
                    number.toString(),
                    fontSize = 24.sp,
                    color = AppPurple
                )
            }

            Text(
                surah,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.amiri_quran)),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}