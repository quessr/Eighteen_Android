package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.ApiResult
import retrofit2.HttpException
import retrofit2.Response

fun <I, O> Response<I>.mapper(action: (I) -> O): O {
    return if (this.isSuccessful) {
        this.body()?.let { action(it) } ?: throw Exception("unknown")
    } else {
        throw HttpException(this)
    }
}

fun <I : ApiResult<T>, T, O> Response<I>.apiResultMapper(action: (I) -> O): O {
    return if (this.isSuccessful) {
        //TODO status 값 에러처리
        val body = body() ?: throw Exception("Unknown")
        val status = body.status ?: throw Exception("Unknown Status")
        action(body)
    } else {
        throw HttpException(this)
    }
}