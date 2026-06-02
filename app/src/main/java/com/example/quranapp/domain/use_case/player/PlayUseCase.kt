package com.example.quranapp.domain.use_case.player

import com.example.quranapp.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayUseCase @Inject constructor(private val playerRepo: PlayerRepository) {
    operator fun invoke(surahNumber: Int, reciterUrlIdentifier: String){
        playerRepo.play(surahNumber, reciterUrlIdentifier)
    }
}