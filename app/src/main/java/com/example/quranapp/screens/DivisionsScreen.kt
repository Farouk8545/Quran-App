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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.BackgroundCanvas
import com.example.quranapp.screens.model.Screens
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun DivisionsScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()

    Box (
        modifier = Modifier.fillMaxSize()
    ){
        BackgroundCanvas()

        Column (
            modifier = Modifier.fillMaxSize()
                .padding(21.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.devisions_header),
                contentDescription = "Divisions",
                modifier = Modifier.padding(16.dp)
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified
            )

            Spacer(Modifier.size(48.dp))

            OutlinedButton (
                onClick = {
                    navViewModel.backStack.add(Screens.AzkarScreen)
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.azkar),
                    fontSize = 16.sp
                )
            }

            OutlinedButton (
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.duaa),
                    fontSize = 16.sp
                )
            }
        }
    }
}