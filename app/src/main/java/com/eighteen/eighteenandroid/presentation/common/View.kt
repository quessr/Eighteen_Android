package com.eighteen.eighteenandroid.presentation.common

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

/** Flow Binding Clicks */
fun View.throttleClick(scope: CoroutineScope, duration: Long = 700L,func: () -> Unit) {
    this.clicks().throttleFirst(duration).onEach { func() }.launchIn(scope)    // FlowBinding ThrottleClick
}

/** 중복 클릭 방지 */
fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L

    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > duration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}
