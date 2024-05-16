package com.eighteen.eighteenandroid.hiltsample.data.repository.di

import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.service.HiltSampleRemoteService
import com.eighteen.eighteenandroid.hiltsample.data.repository.HiltSampleRepositoryImpl
import com.eighteen.eighteenandroid.hiltsample.domain.repository.HiltSampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltSampleRepositoryModule {

    @Provides
    @Singleton
    fun provideHiltSampleRepository(hiltSampleRemoteService: HiltSampleRemoteService): HiltSampleRepository =
        HiltSampleRepositoryImpl(hiltSampleRemoteService = hiltSampleRemoteService)
}