package com.example.quranapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.quranapp.R
import com.example.quranapp.screens.helpercomposable.LanguageDialog
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun SettingsScreen(){

    var showLanguageDialog by remember { mutableStateOf(false) }

    if (showLanguageDialog){
        LanguageDialog {
            showLanguageDialog = false
        }
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(24.dp)
    ){
        Button(
            onClick = {
                showLanguageDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPurple
            )
        ) {
            Text(stringResource(R.string.language))
        }
    }
}