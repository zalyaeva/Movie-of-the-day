package com.android.movieoftheday.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.movieoftheday.data.MovieSource
import com.android.movieoftheday.data.repository.MovieDatabase
import com.android.movieoftheday.model.Movie
import com.android.movieoftheday.model.MovieRemoteKeys
import com.android.movieoftheday.model.base.ListResult
import com.android.movieoftheday.model.base.Result
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRemoteMediator @Inject constructor(
    private val movieSource: MovieSource,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, Movie>() {

    private val movieDao = movieDatabase.movieDao()
    private val movieRemoteKeysDao = movieDatabase.movieRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosetToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstTime(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response: Result<ListResult> = movieSource.getMovieList(page = currentPage)
            if (response is Result.Success<ListResult>) {
                val endOfPaginationReached = response.data?.results.isNullOrEmpty()

                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1

                movieDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDao.clearAllMovie()
                        movieRemoteKeysDao.clearAllRemoteKeys()
                    }

                    response.data?.results?.map { movie ->
                        MovieRemoteKeys(id = movie.id, prevPage, nextPage)
                    }?.let { movieRemoteKeysDao.addAllRemoteKeys(it) }
                    movieDao.addMovieList(response.data?.results ?: ArrayList())
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error((response as Result.Failure).error!!)
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysClosetToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstTime(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            movieRemoteKeysDao.getRemoteKeys(id = it.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            movieRemoteKeysDao.getRemoteKeys(id = it.id)
        }
    }
}