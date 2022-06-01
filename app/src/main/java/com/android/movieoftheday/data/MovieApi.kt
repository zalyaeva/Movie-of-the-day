package com.android.movieoftheday.data

import com.android.movieoftheday.app.retrofit.NetworkModule.Companion.API_KEY
import com.android.movieoftheday.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{movieId}?api_key=$API_KEY&language=ru")
    suspend fun getMovie(@Path("movieId") movieId: String): Movie

    @GET("movie/latest?api_key=$API_KEY&language=ru")
    suspend fun getLatestMovie(): Movie
}