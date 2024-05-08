package com.eighteen.eighteenandroid.data

import com.eighteen.eighteenandroid.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


//TODO 패키지 정리 필요, timeout(기본 10초) 설정 필요할 경우 추가(서버 or 기획 확인 필요)
object ApiClient {
    //fixme 서버 구축 후 base url로 수정
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
    private val defaultOkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
        chain.proceed(request.build())
    }.addNetworkInterceptor(loggingInterceptor).build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                defaultOkHttpClient
            ).build()
}

