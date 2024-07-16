package com.eighteen.eighteenandroid.domain.di

import com.eighteen.eighteenandroid.domain.repository.MessageRepository
import com.eighteen.eighteenandroid.domain.usecase.ConfirmMessageUseCase
import com.eighteen.eighteenandroid.domain.usecase.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageUseCaseModule {
    @Provides
    @Singleton
    fun provideSendMessageUseCase(repository: MessageRepository) = SendMessageUseCase(repository)

    @Provides
    @Singleton
    fun provideConfirmMessageUseCase(repository: MessageRepository) =
        ConfirmMessageUseCase(repository)
}