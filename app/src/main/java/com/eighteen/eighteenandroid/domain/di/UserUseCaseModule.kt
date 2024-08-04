package com.eighteen.eighteenandroid.domain.di

import com.eighteen.eighteenandroid.domain.repository.UserRepository
import com.eighteen.eighteenandroid.domain.usecase.AddSnsLinkUseCase
import com.eighteen.eighteenandroid.domain.usecase.CheckIdValidationUseCase
import com.eighteen.eighteenandroid.domain.usecase.EditIntroduceUseCase
import com.eighteen.eighteenandroid.domain.usecase.MyProfileUseCase
import com.eighteen.eighteenandroid.domain.usecase.RemoveSnsLinkUseCase
import com.eighteen.eighteenandroid.domain.usecase.SignUpUseCase
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

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: UserRepository) = SignUpUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideMyProfileUseCase(repository: UserRepository) =
        MyProfileUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideAddSnsLinkUseCase(repository: UserRepository) =
        AddSnsLinkUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideRemoveSnsLinkUseCase(repository: UserRepository) =
        RemoveSnsLinkUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideEditIntroduceUseCase(repository: UserRepository) =
        EditIntroduceUseCase(repository = repository)
}