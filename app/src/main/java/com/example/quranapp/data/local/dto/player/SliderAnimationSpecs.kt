package com.example.quranapp.data.local.dto.player

import androidx.media3.common.C

data class SliderAnimationSpecs(
    val duration: Long = C.TIME_UNSET,
    val startValue: Float = 0f,
    val targetValue: Float = 0f
)
