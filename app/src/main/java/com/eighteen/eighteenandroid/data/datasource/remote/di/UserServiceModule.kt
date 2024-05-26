package com.eighteen.eighteenandroid.data.datasource.remote.di

import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserServiceModule {
    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}