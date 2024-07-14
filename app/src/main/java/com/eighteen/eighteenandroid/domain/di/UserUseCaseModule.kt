package com.eighteen.eighteenandroid.domain.di

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import com.eighteen.eighteenandroid.domain.usecase.CheckIdValidationUseCase
import com.eighteen.eighteenandroid.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    @Singleton
    fun provideUserUseCase(repository: UserRepository): UserUseCase =
        UserUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideCheckIdValidationUseCase(repository: UserRepository) =
        CheckIdValidationUseCase(repository = repository)
}