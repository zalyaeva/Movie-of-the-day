package com.android.movieoftheday.app.retrofit

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    class Success<T>(val data: T?) : Result<T>()
    class Failure(val error: Throwable? = null, val errorString: String? = null) : Result<Nothing>()
}