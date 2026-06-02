package com.example.quranapp.data.local.dto.player

import androidx.media3.common.Player

data class PlayerState(
    var isPlaying: Boolean = false,
    var playbackState: Int = Player.STATE_IDLE,
    var isReady: Boolean = false
)
