package com.eighteen.eighteenandroid.presentation.common

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("DEPRECATION")
fun <T> Bundle.getParcelableOrNull(key: String, clazz: Class<T>): T? =
    if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) getParcelable(key, clazz)
    else getParcelable(key)


@Suppress("DEPRECATION")
fun <T : Parcelable> Bundle.getParcelableArrayListOrNull(
    key: String,
    clazz: Class<T>
): ArrayList<out T>? =
    if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) getParcelableArrayList(key, clazz)
    else getParcelableArrayList(key)

fun <T : Parcelable> Bundle.getParcelableListOrNull(key: String, clazz: Class<T>): List<T>? =
    getParcelableArrayListOrNull(key, clazz)?.toList()