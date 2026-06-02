package com.example.quranapp.domain.use_case.player

import com.example.quranapp.data.local.dto.player.SliderAnimationSpecs
import com.example.quranapp.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SliderAnimationStateUseCase @Inject constructor(
    private val repo: PlayerRepository
){
    operator fun invoke(): StateFlow<SliderAnimationSpecs> {
        return repo.sliderAnimationState
    }
}