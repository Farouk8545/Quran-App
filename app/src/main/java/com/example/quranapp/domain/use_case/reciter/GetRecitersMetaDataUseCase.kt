package com.example.quranapp.domain.use_case.reciter

import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.domain.repository.AssetsRepository
import javax.inject.Inject

class GetRecitersMetaDataUseCase @Inject constructor(
    private val repo: AssetsRepository
) {
    operator fun invoke(): List<ReciterMetaData> {
        return repo.getRecitersMetaData()
    }
}