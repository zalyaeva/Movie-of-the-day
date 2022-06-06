package com.android.movieoftheday.data

import com.android.movieoftheday.model.base.Result
import com.android.movieoftheday.model.Movie
import com.android.movieoftheday.model.base.ListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.system.measureTimeMillis


@Singleton
class MovieSource @Inject constructor(
    private val movieApi: MovieApi
) {

    private val standardMaxMovieCount = 900000L

    suspend fun getMovieList(page: Int): Result<ListResult> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(movieApi.getMovieList(page))
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    private suspend fun getLastMovieId(): Result<Long> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(movieApi.getLatestMovie().id)
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    suspend fun getRandomMovie(): Result<Movie> = withContext(Dispatchers.IO) {
        val maxMovieId = measureTimeMillis {
            try {
                when (val data = getLastMovieId()) {
                    is Result.Success -> data.data
                    is Result.Failure -> standardMaxMovieCount
                    else -> standardMaxMovieCount
                }
            } catch (exception: Exception) {
                standardMaxMovieCount
            }
        }

        val randomIdMovie = Random.nextLong(1, maxMovieId).toString()

        return@withContext try {
            Result.Success(movieApi.getMovie(randomIdMovie))
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }
}