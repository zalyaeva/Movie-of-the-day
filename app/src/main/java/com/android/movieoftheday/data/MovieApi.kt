package com.android.movieoftheday.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movieId}")
    fun getMovie(@Query("movieId") movieId: String)
}