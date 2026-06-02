package com.example.quranapp.domain.use_case.surah

import com.example.quranapp.data.local.dto.surahs.SurahMetaData
import com.example.quranapp.domain.repository.AssetsRepository
import javax.inject.Inject

class GetSurahMetaDataUseCase @Inject constructor(
    private val repo: AssetsRepository
) {
    operator fun invoke(): List<SurahMetaData> {
        return repo.getSurahMetaData()
    }
}