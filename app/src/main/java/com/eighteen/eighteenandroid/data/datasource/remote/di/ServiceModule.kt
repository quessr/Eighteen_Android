package com.eighteen.eighteenandroid.data.datasource.remote.di

import com.eighteen.eighteenandroid.data.datasource.remote.service.ChatService
import com.eighteen.eighteenandroid.data.datasource.remote.service.MessageService
import com.eighteen.eighteenandroid.data.datasource.remote.service.MyPageService
import com.eighteen.eighteenandroid.data.datasource.remote.service.SchoolService
import com.eighteen.eighteenandroid.data.datasource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideMessageService(retrofit: Retrofit): MessageService =
        retrofit.create(MessageService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideSchoolService(retrofit: Retrofit): SchoolService =
        retrofit.create(SchoolService::class.java)

    @Singleton
    @Provides
    fun provideChatService(retrofit: Retrofit): ChatService =
        retrofit.create(ChatService::class.java)

    @Singleton
    @Provides
    fun provideMyPageService(retrofit: Retrofit): MyPageService =
        retrofit.create(MyPageService::class.java)
}
