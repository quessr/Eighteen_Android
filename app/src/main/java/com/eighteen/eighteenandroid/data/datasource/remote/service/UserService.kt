package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.request.SignUpRequest
import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import com.eighteen.eighteenandroid.data.datasource.remote.response.SignUpResponse
import com.eighteen.eighteenandroid.data.datasource.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/api/v1/user/profile")
    suspend fun getUserInfo(): Response<List<UserResponse>>

    @POST("/v1/api/user/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpRequest): Response<ApiResult<SignUpResponse>>
}