package com.example.quranapp.domain.use_case.player

import com.example.quranapp.data.local.dto.player.PlayerState
import com.example.quranapp.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PlayerStateUseCase @Inject constructor(
    private val repo: PlayerRepository
) {
    operator fun invoke(): StateFlow<PlayerState> {
        return repo.playerState
    }
}