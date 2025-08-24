package com.example.quranapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.api.models.azkar.SpecifiedAzkarModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.ui.theme.DarkGold
import com.example.quranapp.ui.theme.LightGold

@Composable
fun DisplayDuaaScreen(
    duaList: List<SpecifiedAzkarModel>,
    duaCategory: Int
){

    val navViewModel: MainNavigationViewModel = hiltViewModel()

    val pagerState = rememberPagerState { duaList.size }

    val indicatorPages = if (pagerState.pageCount == 1){
        listOf(0)
    }else{
        when (pagerState.currentPage) {
            0 -> listOf(0, 1, 2)
            pagerState.pageCount - 1 -> listOf(pagerState.pageCount - 3, pagerState.pageCount - 2, pagerState.pageCount - 1)
            else -> listOf(pagerState.currentPage - 1, pagerState.currentPage, pagerState.currentPage + 1)
        }
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.duaa_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.padding(16.dp)
                .size(32.dp)
                .align(Alignment.TopStart)
                .clickable(
                    onClick = {
                        navViewModel.backStack.removeLastOrNull()
                    }
                )
        )

        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Box (
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1.75f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(duaCategory),
                    style = TextStyle(
                        fontSize = 48.sp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                LightGold,
                                DarkGold,
                                LightGold
                            )
                        )
                    )
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) { page ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            text = duaList[page].text,
                            fontSize = 24.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily(Font(R.font.amiri_quran)),
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.size(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                indicatorPages.forEach { index ->
                    val isActive = index == pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (isActive) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (isActive) Color.White else Color.Gray)
                    )
                }
            }
        }
    }
}