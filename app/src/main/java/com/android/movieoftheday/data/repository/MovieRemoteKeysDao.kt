package com.android.movieoftheday.data.repository

import androidx.room.*
import com.android.movieoftheday.model.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Transaction
    @Query("SELECT * FROM top_rated_movie_remoted_keys_database WHERE id= :id")
    suspend fun getRemoteKeys(id: Long?): MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(list: List<MovieRemoteKeys>)

    @Query("DELETE FROM top_rated_movie_remoted_keys_database")
    suspend fun clearAllRemoteKeys()
}