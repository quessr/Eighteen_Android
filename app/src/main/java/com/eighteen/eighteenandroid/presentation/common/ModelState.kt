package com.eighteen.eighteenandroid.presentation.common

sealed interface ModelState<T> {
    val data: T?

    class Empty<T> : ModelState<T> {
        override val data: T? = null
    }

    class Loading<T>(override val data: T? = null) : ModelState<T>
    class Success<T>(override val data: T? = null) : ModelState<T>
    class Error<T>(override val data: T? = null, val throwable: Throwable) : ModelState<T>

    fun isSuccess() = this is Success
    fun <T> copyWithData(data: T? = null) = when (this) {
        is Success -> Success(data)
        is Loading -> Loading(data)
        is Error -> Error(data, throwable)
        is Empty -> Empty()
    }
}