package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quranapp.ui.theme.LavenderBackground

@Composable
fun LanguageDialog(
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    title: String,
    onDismiss: () -> Unit
){

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card (
            modifier = Modifier.width(300.dp)
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LavenderBackground
            )
        ){
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    title,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                options.forEach { option ->
                    Button(
                        onClick = {
                            onOptionSelected(option)
                            onDismiss()
                        },
                        modifier = Modifier.width(100.dp)
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Text(
                            option,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}