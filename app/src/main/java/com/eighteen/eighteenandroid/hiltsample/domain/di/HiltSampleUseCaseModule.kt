package com.eighteen.eighteenandroid.hiltsample.domain.di

import com.eighteen.eighteenandroid.hiltsample.domain.repository.HiltSampleRepository
import com.eighteen.eighteenandroid.hiltsample.domain.usecase.HiltSampleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltSampleUseCaseModule {
    @Provides
    @Singleton
    fun provideHiltSampleUseCase(repository: HiltSampleRepository): HiltSampleUseCase =
        HiltSampleUseCase(repository = repository)
}