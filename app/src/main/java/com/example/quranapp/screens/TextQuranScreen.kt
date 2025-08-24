package com.example.quranapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.quranapp.api.models.quran.Verse
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.fontsize.FontSizeController
import com.example.quranapp.screens.helpercomposable.LoadingScreen
import com.example.quranapp.ui.theme.WarmGray

@Composable
fun TextQuranScreen(surahNumber: Int){

    val apiViewModel: ApiViewModel = hiltViewModel()

    val surah by apiViewModel.textSurah.collectAsState()
    val fontSize = FontSizeController.fontSize.floatValue

    LaunchedEffect(Unit) {
        apiViewModel.getTextSurah(surahNumber)
    }

    if (surah == null){
        LoadingScreen()
        return
    }

    val text = remember(surah) {
        renderFullText(surah?.verses ?: emptyList())
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
                    text = surah?.name ?: "Surah",
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
                    text = "${stringResource(R.string.ayahs_count)}\n${surah?.verses?.size}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.amiri_quran)),
                    modifier = Modifier.align(Alignment.CenterStart)
                        .padding(start = 32.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (surahNumber != 1 && surahNumber != 9){
                    Text(
                        text = "بِسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيمِ",
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.amiri_quran)),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.size(24.dp))
                }
            }
        }

        item {
            Text(
                text = text,
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(R.font.amiri_quran)),
                color = Color.Black,
                textAlign = TextAlign.Justify,
                style = LocalTextStyle.current.copy(
                    textDirection = TextDirection.Rtl,
                    lineHeight = (fontSize * 2f).sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp) // no bottom padding
            )
        }
    }
}

fun renderFullText(text: List<Verse>): String{
    return buildAnnotatedString {
        text.forEach {
            append(it.text)
            append(" ")
            append("﴿${it.id}﴾")
            append(" ")
        }
    }.toString()
}