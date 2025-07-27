package com.example.quranapp.api

import com.example.quranapp.api.models.edition.ApiEditionResponseModel
import com.example.quranapp.api.models.surah.ApiSurahResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("surah/{surah}/{edition}")
    suspend fun getSurah(@Path("surah") surah: Int, @Path("edition") edition: String?): Response<ApiSurahResponseModel>

    @GET("edition")
    suspend fun getEditions(
        @Query("format") format: String,
        @Query("language") language: String,
        @Query("type") type: String
        ): Response<ApiEditionResponseModel>

}