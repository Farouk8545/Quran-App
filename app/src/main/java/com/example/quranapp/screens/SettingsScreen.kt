package com.example.quranapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.fontsize.FontSizeController
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.BackgroundCanvas
import com.example.quranapp.screens.helpercomposable.LanguageDialog
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()

    var showLanguageDialog by remember { mutableStateOf(false) }
    var showFontDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val languageOptions = remember {
        listOf(
            "English",
            "العربية"
        )
    }
    val fontOptions = remember {
        listOf(
            context.getString(R.string.small),
            context.getString(R.string.medium),
            context.getString(R.string.large),
            context.getString(R.string.very_large)
        )
    }
    val fontMap = mapOf<String, Float>(
        context.getString(R.string.small) to 16f,
        context.getString(R.string.medium) to 24f,
        context.getString(R.string.large) to 32f,
        context.getString(R.string.very_large) to 48f
    )

    if (showLanguageDialog){
        LanguageDialog(
            languageOptions,
            title = stringResource(R.string.select_language),
            onOptionSelected = {
                languageViewModel.changeLanguage(if (it == "English") "en" else "ar")
            },
        ) {
            showLanguageDialog = false
        }
    }

    if (showFontDialog){
        LanguageDialog(
            options = fontOptions,
            title = stringResource(R.string.select_font),
            onOptionSelected = {
                coroutineScope.launch {
                    FontSizeController.saveFont(context, fontMap[it]!!)
                }
            }
        ) {
            showFontDialog = false
        }
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ){
        BackgroundCanvas()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(21.dp)
        ){
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Divisions",
                modifier = Modifier
                    .padding(16.dp)
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Black
            )

            Spacer(Modifier.size(48.dp))

            OutlinedButton (
                onClick = {
                    showLanguageDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.language),
                    fontSize = 16.sp
                )
            }

            OutlinedButton (
                onClick = {
                    showFontDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.font),
                    fontSize = 16.sp
                )
            }
        }
    }
}