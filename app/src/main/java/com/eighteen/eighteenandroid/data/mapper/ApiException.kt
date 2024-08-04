package com.eighteen.eighteenandroid.data.mapper

/**
 * api code에 따른 Exception class 필요시 추가
 * 필요시 추가바람
 * @property code : 해당 Exception에 대응되는 response code
 */
sealed class ApiException : Exception() {
    abstract val code: Int

    object BadRequest : ApiException() {
        override val code: Int = 400
    }

    object UnAuthorized : ApiException() {
        override val code: Int = 401
    }

    object Forbidden : ApiException() {
        override val code: Int = 403
    }

    object NotFound : ApiException() {
        override val code: Int = 404
    }

    object Unknown : ApiException() {
        override val code: Int = -1
    }
}

fun findApiExceptionByCode(code: Int) = when (code) {
    400 -> ApiException.BadRequest
    401 -> ApiException.UnAuthorized
    403 -> ApiException.Forbidden
    404 -> ApiException.NotFound
    else -> ApiException.Unknown
}

