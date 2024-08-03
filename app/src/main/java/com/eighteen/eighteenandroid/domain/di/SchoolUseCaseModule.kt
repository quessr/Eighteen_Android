package com.eighteen.eighteenandroid.domain.di

import com.eighteen.eighteenandroid.domain.repository.SchoolRepository
import com.eighteen.eighteenandroid.domain.usecase.GetSchoolsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchoolUseCaseModule {

    @Provides
    @Singleton
    fun provideGetSchoolsUseCase(schoolRepository: SchoolRepository) =
        GetSchoolsUseCase(schoolRepository = schoolRepository)
}