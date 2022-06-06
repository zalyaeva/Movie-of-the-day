package com.android.movieoftheday.model.base

import com.android.movieoftheday.model.Movie

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    class Success<T>(val data: T?) : Result<T>()
    class Failure(val error: Throwable? = null, val errorString: String? = null) : Result<Nothing>()
}

data class ListResult(
    val page: Int,
    val results: List<Movie>
)