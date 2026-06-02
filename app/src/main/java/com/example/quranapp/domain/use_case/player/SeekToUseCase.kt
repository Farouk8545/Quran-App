package com.example.quranapp.domain.use_case.player

import com.example.quranapp.domain.repository.PlayerRepository
import javax.inject.Inject

class SeekToUseCase @Inject constructor(
    private val repo: PlayerRepository
) {
    operator fun invoke(fraction: Float) {
        repo.seekTo(fraction)
    }
}