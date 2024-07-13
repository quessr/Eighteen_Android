package com.eighteen.eighteenandroid.data.repository.di

import com.eighteen.eighteenandroid.data.repository.MessageRepositoryImpl
import com.eighteen.eighteenandroid.data.repository.UserRepositoryImpl
import com.eighteen.eighteenandroid.domain.repository.MessageRepository
import com.eighteen.eighteenandroid.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository
}