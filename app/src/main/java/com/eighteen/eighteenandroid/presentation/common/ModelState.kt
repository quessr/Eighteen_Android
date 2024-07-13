package com.eighteen.eighteenandroid.presentation.common

sealed interface ModelState<T> {

    val data: T?

    class Loading<T>(override val data: T?) : ModelState<T>
    class Success<T>(override val data: T?) : ModelState<T>
    class Error<T>(override val data: T?, throwable: Throwable) : ModelState<T>
}