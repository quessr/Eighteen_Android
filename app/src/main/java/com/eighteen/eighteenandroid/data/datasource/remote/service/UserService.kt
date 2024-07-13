package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("/api/v1/user/profile")
    suspend fun getUserInfo(): Response<List<UserResponse>>
}