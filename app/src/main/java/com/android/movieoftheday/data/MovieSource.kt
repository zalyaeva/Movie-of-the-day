package com.android.movieoftheday.data

import com.android.movieoftheday.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
class MovieSource @Inject constructor(private val movieApi: MovieApi) {

    private suspend fun getLastMovieId(): Long = withContext(Dispatchers.IO) {
        return@withContext movieApi.getLatestMovie().id
    }

    suspend fun getRandomMovie(): Movie = withContext(Dispatchers.IO) {
        val maxMovieId = getLastMovieId()
        val randomIdMovie = Random.nextLong(1, maxMovieId).toString()
        return@withContext movieApi.getMovie(randomIdMovie)
    }
}