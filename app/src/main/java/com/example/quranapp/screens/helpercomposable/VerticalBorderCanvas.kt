package com.example.quranapp.screens.helpercomposable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun VerticalBorderCanvas(modifier: Modifier = Modifier, heightPx: Float) {
    Canvas(
        modifier = modifier.height(with(LocalDensity.current) { heightPx.toDp() })
            .width(20.dp)
    ) {
        val width = size.width
        val height = size.height

        val gold = Color(0xFFB89C6A)
        val dark = Color(0xFF4B3E2A)

        // Background
        drawRect(color = Color(0xFFF3F2EE), size = size)

        // Outer line
        drawRect(
            color = dark,
            topLeft = Offset(2f, 2f),
            size = size.copy(width = width - 4f, height = height - 4f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
        )

        // Decorative pattern
        val tileHeight = 24f
        var y = 8f

        while (y < height - tileHeight) {
            drawPath(
                path = Path().apply {
                    moveTo(width / 2, y)
                    lineTo(width - 4f, y + tileHeight / 2)
                    lineTo(width / 2, y + tileHeight)
                    lineTo(4f, y + tileHeight / 2)
                    close()
                } as Path,
                color = gold
            )
            y += tileHeight + 8f
        }
    }
}