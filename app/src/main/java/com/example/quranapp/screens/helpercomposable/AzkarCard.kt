package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.ui.theme.AppPurple
import com.example.quranapp.ui.theme.LavenderBackground

@Composable
fun AzkarCard(
    icon: Int,
    title: Int,
    onClick: () -> Unit
){
    Card (
        modifier = Modifier.fillMaxWidth()
            .border(2.dp, AppPurple, RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = LavenderBackground
        )
    ){
        Column {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(title),
                modifier = Modifier.padding(24.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                tint = Color.Unspecified
            )

            Text(
                text = stringResource(title),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
    }
}