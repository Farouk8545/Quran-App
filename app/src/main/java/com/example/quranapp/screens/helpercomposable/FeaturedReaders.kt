package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.R
import com.example.quranapp.models.FeaturedModel
import kotlinx.coroutines.launch

@Composable
fun FeaturedReaders(
    pagerItems: List<FeaturedModel>,
    modifier: Modifier = Modifier,
    onClick: (FeaturedModel) -> Unit
){
    val pagerState = rememberPagerState { pagerItems.size }
    val coroutineScope = rememberCoroutineScope()

    Box (modifier = modifier){
        Column {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    onClick = {
                        coroutineScope.launch {
                            val targetPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                            pagerState.animateScrollToPage(targetPage)
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Left",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.best_voices),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )

                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    onClick = {
                        coroutineScope.launch {
                            val targetPage = (pagerState.currentPage + 1).coerceAtMost(pagerItems.size - 1)
                            pagerState.animateScrollToPage(targetPage)
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Right",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.size(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.clickable(
                            onClick = {
                                onClick(pagerItems[it])
                            }
                        )
                    ) {
                        Image(
                            painter = pagerItems[it].image,
                            contentDescription = pagerItems[it].name,
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Box (
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .align(Alignment.BottomStart)
                        ){
                            Text(
                                text = pagerItems[it].name,
                                modifier = Modifier.padding(24.dp),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}