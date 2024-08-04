package com.eighteen.eighteenandroid.domain.di

import com.eighteen.eighteenandroid.domain.repository.ChatRepository
import com.eighteen.eighteenandroid.domain.usecase.EnterChatRoomUseCase
import com.eighteen.eighteenandroid.domain.usecase.GetChatRoomsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatUseCaseModule {
    @Provides
    @Singleton
    fun provideEnterChatRoomUseCase(repository: ChatRepository) =
        EnterChatRoomUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetChatRoomsUseCase(repository: ChatRepository) =
        GetChatRoomsUseCase(repository = repository)
}