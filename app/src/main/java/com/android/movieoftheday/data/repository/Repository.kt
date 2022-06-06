package com.android.movieoftheday.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.movieoftheday.data.MovieSource
import com.android.movieoftheday.data.paging.MovieRemoteMediator
import com.android.movieoftheday.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val movieSource: MovieSource,
    private val movieDatabase: MovieDatabase
) {

    fun getMovieList(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = {movieDatabase.movieDao().getAllMovie()}
        return Pager(
            config = PagingConfig(20),
            remoteMediator = MovieRemoteMediator(movieSource, movieDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}