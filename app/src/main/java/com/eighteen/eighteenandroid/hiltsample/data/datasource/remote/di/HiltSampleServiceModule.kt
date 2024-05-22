package com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.di

import com.eighteen.eighteenandroid.hiltsample.data.datasource.remote.service.HiltSampleRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltSampleServiceModule {
    @Singleton
    @Provides
    fun provideHiltSampleRemoteService(retrofit: Retrofit): HiltSampleRemoteService =
        retrofit.create(HiltSampleRemoteService::class.java)
}