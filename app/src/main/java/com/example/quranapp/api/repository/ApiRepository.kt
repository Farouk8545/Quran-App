package com.example.quranapp.api.repository

import com.example.quranapp.api.RetrofitInstance
import com.example.quranapp.api.models.edition.ApiEditionResponseModel
import com.example.quranapp.api.models.surah.ApiSurahResponseModel
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor() {

    suspend fun getSurah(surah: Int, edition: String?): Response<ApiSurahResponseModel> {
        return RetrofitInstance.api.getSurah(surah, edition)
    }

    suspend fun getEditions(format: String, language: String, type: String): Response<ApiEditionResponseModel>{
        return RetrofitInstance.api.getEditions(format, language, type)
    }

}