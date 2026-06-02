package com.example.quranapp.domain.use_case.player

import com.example.quranapp.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayNextUseCase @Inject constructor(
    private val repo: PlayerRepository
){
    operator fun invoke(): Result<Unit> {
        return repo.playNext()
    }
}