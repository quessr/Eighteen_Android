package com.eighteen.eighteenandroid.hiltsample.data.repository.di

import com.eighteen.eighteenandroid.hiltsample.data.repository.HiltSampleRepositoryImpl
import com.eighteen.eighteenandroid.hiltsample.domain.repository.HiltSampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HiltSampleRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHiltSampleRepository(hiltSampleRemoteRepositoryImpl: HiltSampleRepositoryImpl): HiltSampleRepository
}