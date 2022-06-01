package com.android.movieoftheday.data

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T> observe(data: LiveData<T>): T? {
    var result by remember { mutableStateOf(data.value) }
    val observer = remember { Observer<T> { result = it } }

    DisposableEffect(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }

    return result
}