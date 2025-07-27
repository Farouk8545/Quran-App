package com.example.quranapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.screens.helpercomposable.HorizontalBorderCanvas
import com.example.quranapp.screens.helpercomposable.LoadingScreen
import com.example.quranapp.screens.helpercomposable.VerticalBorderCanvas
import com.example.quranapp.ui.theme.WarmGray

@Composable
fun TextQuranScreen(surahNumber: Int){

    val apiViewModel: ApiViewModel = hiltViewModel()

    val surah by apiViewModel.textSurah.collectAsState()

    var contentHeight by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        apiViewModel.getTextSurah(surahNumber)
    }

    val annotatedString = buildAnnotatedString {
        surah?.ayahs?.forEachIndexed { index,ayah ->
            if (index == 0 && surahNumber != 1 && surahNumber != 9){
                append(ayah.text.replace("\n", "").split(" ").drop(4).joinToString(" ").trim())
            }else{
                append(ayah.text.replace("\n", "").trim())
            }
            append(" ")
            append("﴿${ayah.numberInSurah}﴾")
            append(" ")
        }
    }

    LazyColumn (
        modifier = Modifier.fillMaxSize()
            .background(WarmGray)
    ){
        //surah name holder
        item {
            Box (
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.surah_name_holder),
                    contentDescription = "Surah Name Holder",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )

                Text(
                    text = surah?.name?.split(" ")?.drop(1)?.joinToString(" ") ?: "Surah",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    modifier = Modifier.align(Alignment.Center)
                )

                Text(
                    text = "${stringResource(R.string.surah_number)}\n$surahNumber",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = 30.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${stringResource(R.string.ayahs_count)}\n${surah?.ayahs?.size}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    modifier = Modifier.align(Alignment.CenterStart)
                        .padding(start = 32.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        //border with the quran text
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                VerticalBorderCanvas(
                    heightPx = contentHeight.toFloat(),
                    modifier = Modifier.padding(start = 8.dp)
                )

                Column (
                    modifier = Modifier.weight(1f)
                        .padding(
                            bottom = 24.dp
                        )
                        .onGloballyPositioned{
                            contentHeight = it.size.height
                        }
                ){
                    HorizontalBorderCanvas()
                    if (surahNumber != 1 && surahNumber != 9){
                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيمِ",
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.amiri_quran)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(Modifier.size(24.dp))
                    }

                    Text(
                        text = annotatedString,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.amiri_quran)),
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        style = LocalTextStyle.current.copy(
                            textDirection = TextDirection.Rtl,
                            lineHeight = 50.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                bottom = 16.dp
                            )
                    )
                    HorizontalBorderCanvas()
                }
                VerticalBorderCanvas(
                    heightPx = contentHeight.toFloat(),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}