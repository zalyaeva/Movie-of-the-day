package com.android.movieoftheday.data.repository

import androidx.paging.PagingSource
import androidx.room.*
import com.android.movieoftheday.model.Movie

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM top_rated_movie_database")
    fun getAllMovie(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieList(list: List<Movie>)

    @Query("DELETE FROM top_rated_movie_database")
    suspend fun clearAllMovie()
}