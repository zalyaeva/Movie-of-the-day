package com.android.movieoftheday.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.movieoftheday.App

@Entity(tableName = "top_rated_movie_remoted_keys_database")
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val prevPage: Int?,
    val nextPage: Int?
)
