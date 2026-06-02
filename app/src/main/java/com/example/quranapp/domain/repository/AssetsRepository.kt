package com.example.quranapp.domain.repository

import com.example.quranapp.data.local.dto.reciters.ReciterMetaData
import com.example.quranapp.data.local.dto.surahs.SurahMetaData

interface AssetsRepository {
    fun getRecitersMetaData(): List<ReciterMetaData>
    fun getSingleReciterMetaData(reciterUrlIdentifier: String): ReciterMetaData
    fun getSurahMetaData(): List<SurahMetaData>
}