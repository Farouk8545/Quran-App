package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import com.example.quranapp.ui.theme.AppPurple

@Composable
fun BackgroundCanvas(){
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.4f)

            quadraticTo(
                size.width * 0.35f, size.height * 0.2f,
                size.width, size.height * 0.4f
            )

            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = path,
            color = AppPurple
        )
    }
}