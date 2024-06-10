package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.dao.UserDao
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("/api/v1/user/profile")
    suspend fun getUserInfo(): Response<List<UserDao>>
}