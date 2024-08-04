package com.eighteen.eighteenandroid.data.mapper

import retrofit2.Response

fun <I, O> Response<I>.mapper(action: (I) -> O): O {
    return if (this.isSuccessful) {
        this.body()?.let { action(it) } ?: throw Exception("unknown")
    } else {
        throw findApiExceptionByCode(code = code())
    }
}