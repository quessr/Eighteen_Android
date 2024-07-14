package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.request.SendMessageRequest
import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageService {
    @POST("/v1/api/message/send")
    suspend fun postSendMessage(@Body sendMessageRequest: SendMessageRequest): Response<ApiResult<String>>
}