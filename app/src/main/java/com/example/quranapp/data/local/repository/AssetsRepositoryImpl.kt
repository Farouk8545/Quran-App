package com.example.quranapp.data.local.repository

import com.example.quranapp.data.local.assets.LocalMetaDataExtractor
import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.data.local.dto.surahs.SurahMetaData
import com.example.quranapp.domain.repository.AssetsRepository
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val extractor: LocalMetaDataExtractor
): AssetsRepository {

    override fun getRecitersMetaData(): List<ReciterMetaData> {
        return extractor.getRecitersMetaData()
    }

    override fun getSingleReciterMetaData(reciterUrlIdentifier: String): ReciterMetaData {
        return extractor.getSingleReciterMetaData(reciterUrlIdentifier)
    }

    override fun getSurahMetaData(): List<SurahMetaData> {
        return extractor.getSurahMetaData()
    }
}