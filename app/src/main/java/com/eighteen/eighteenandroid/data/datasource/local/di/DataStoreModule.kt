package com.eighteen.eighteenandroid.data.datasource.local.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    //TODO local properties로 이름 이동
    @Singleton
    @Provides
    fun providePreferenceDataStore(@ApplicationContext context: Context) = PreferenceDataStoreFactory.create(
        produceFile = {context.preferencesDataStoreFile("test")}
    )
}