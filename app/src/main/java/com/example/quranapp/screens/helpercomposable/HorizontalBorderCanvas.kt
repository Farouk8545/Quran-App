package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalBorderCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxWidth().height(20.dp)) {
        val width = size.width
        val height = size.height

        val gold = Color(0xFFB89C6A)
        val dark = Color(0xFF4B3E2A)

        drawRect(color = Color(0xFFF3F2EE), size = size)

        // Outer border
        drawRect(
            color = dark,
            topLeft = Offset(2f, 2f),
            size = size.copy(width = width - 4f, height = height - 4f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
        )

        // Decorative diamonds
        val tileWidth = 24f
        var x = 8f

        while (x < width - tileWidth) {
            drawPath(
                path = Path().apply {
                    moveTo(x + tileWidth / 2, 2f)
                    lineTo(x + tileWidth, height / 2)
                    lineTo(x + tileWidth / 2, height - 2f)
                    lineTo(x, height / 2)
                    close()
                },
                color = gold
            )
            x += tileWidth + 8f
        }
    }
}

