package com.eighteen.eighteenandroid.data.datasource.remote.service

import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import com.eighteen.eighteenandroid.data.datasource.remote.response.ChatResponse
import com.eighteen.eighteenandroid.data.datasource.remote.response.ChatRoomResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {
    @GET("/v1/api/chat/enter/{chatroomInfold}")
    fun getChatMessages(
        @Path("chatroomInfold") chatRoomId: String,
        @Query("requestTime") requestTime: String
    ): Response<ApiResult<List<ChatResponse>>>

    @GET("/v1/api/chat/all/{senderNo}")
    fun getChatRooms(
        @Path("senderNo") senderNo: Int
    ): Response<ApiResult<List<ChatRoomResponse>>>
}