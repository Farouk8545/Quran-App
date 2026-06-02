package com.example.quranapp.core.di

import android.content.Context
import com.example.quranapp.data.local.assets.LocalMetaDataExtractor
import com.example.quranapp.data.local.assets.UrlBuilder
import com.example.quranapp.data.local.repository.AssetsRepositoryImpl
import com.example.quranapp.domain.repository.AssetsRepository
import com.example.quranapp.domain.use_case.reciter.GetRecitersMetaDataUseCase
import com.example.quranapp.domain.use_case.surah.GetSurahMetaDataUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AssetsBindModule {

    @Binds
    @Singleton
    abstract fun bindAssetsRepository(impl: AssetsRepositoryImpl): AssetsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AssetsModule {

    @Provides
    @Singleton
    fun providesLocalMetaDataExtractor(@ApplicationContext context: Context) =
        LocalMetaDataExtractor(context)

    @Provides
    @Singleton
    fun providesGetRecitersMetaDataUseCase(assetsRepo: AssetsRepository): GetRecitersMetaDataUseCase =
        GetRecitersMetaDataUseCase(assetsRepo)

    @Provides
    @Singleton
    fun providesGetSurahMetaDataUseCase(repo: AssetsRepository): GetSurahMetaDataUseCase =
        GetSurahMetaDataUseCase(repo)

    @Provides
    @Singleton
    fun providesUrlBuilder(): UrlBuilder = UrlBuilder()
}