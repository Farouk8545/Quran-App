package com.example.quranapp.core.di

import com.example.quranapp.data.local.repository.PlayerRepositoryImpl
import com.example.quranapp.domain.repository.PlayerRepository
import com.example.quranapp.domain.use_case.player.CanSeekUseCase
import com.example.quranapp.domain.use_case.player.DisplayStateUseCase
import com.example.quranapp.domain.use_case.player.GetCurrentPositionUseCase
import com.example.quranapp.domain.use_case.player.PlayNextUseCase
import com.example.quranapp.domain.use_case.player.PlayPreviousUseCase
import com.example.quranapp.domain.use_case.player.PlayUseCase
import com.example.quranapp.domain.use_case.player.SliderAnimationStateUseCase
import com.example.quranapp.domain.use_case.player.PlayerStateUseCase
import com.example.quranapp.domain.use_case.player.ReleaseUseCase
import com.example.quranapp.domain.use_case.player.SeekToUseCase
import com.example.quranapp.domain.use_case.player.TogglePlayPauseUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaBindModule {
    @Binds
    @Singleton
    abstract fun bindPlayerRepository(repo: PlayerRepositoryImpl): PlayerRepository
}

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    @Singleton
    fun providePlayUseCase(playerRepo: PlayerRepository): PlayUseCase = PlayUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesReleaseUseCase(playerRepo: PlayerRepository): ReleaseUseCase =
        ReleaseUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesPlayerStateUseCase(playerRepo: PlayerRepository): PlayerStateUseCase =
        PlayerStateUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesDisplayStateUseCase(playerRepo: PlayerRepository): DisplayStateUseCase =
        DisplayStateUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesTogglePlayPauseUseCase(playerRepo: PlayerRepository): TogglePlayPauseUseCase =
        TogglePlayPauseUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesPlayNextUseCase(playerRepo: PlayerRepository): PlayNextUseCase =
        PlayNextUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesPlayPreviousUseCase(playerRepo: PlayerRepository): PlayPreviousUseCase =
        PlayPreviousUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesPlaybackStateUseCase(playerRepo: PlayerRepository): SliderAnimationStateUseCase =
        SliderAnimationStateUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesGetCurrentPositionUseCase(playerRepo: PlayerRepository): GetCurrentPositionUseCase =
        GetCurrentPositionUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesSeekToUseCase(playerRepo: PlayerRepository): SeekToUseCase =
        SeekToUseCase(playerRepo)

    @Provides
    @Singleton
    fun providesCanSeekUseCase(playerRepo: PlayerRepository): CanSeekUseCase =
        CanSeekUseCase(playerRepo)
}