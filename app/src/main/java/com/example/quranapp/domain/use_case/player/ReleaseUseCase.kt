package com.example.quranapp.domain.use_case.player

import com.example.quranapp.domain.repository.PlayerRepository
import javax.inject.Inject

class ReleaseUseCase @Inject constructor(
    private val repo: PlayerRepository
) {
    operator fun invoke() {
        repo.release()
    }
}