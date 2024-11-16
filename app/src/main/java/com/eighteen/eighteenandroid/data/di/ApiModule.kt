package com.eighteen.eighteenandroid.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.eighteen.eighteenandroid.BuildConfig
import com.eighteen.eighteenandroid.data.retrofit.AuthHeaderInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL: String = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(authHeaderInterceptor: AuthHeaderInterceptor): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
            chain.proceed(request.build())
        }.addNetworkInterceptor(loggingInterceptor).addInterceptor(authHeaderInterceptor).build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build()
    }


    @Singleton
    @Provides
    fun provideAuthHeaderInterceptor(preferenceDatastore: DataStore<Preferences>) =
        AuthHeaderInterceptor(preferenceDatastore)
}