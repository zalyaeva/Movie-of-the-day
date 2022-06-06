package com.android.movieoftheday.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.movieoftheday.model.Genre
import com.android.movieoftheday.model.GenreConverter
import com.android.movieoftheday.model.Movie
import com.android.movieoftheday.model.MovieRemoteKeys

@Database(entities = [Movie::class, MovieRemoteKeys::class], version = 2, exportSchema = false)
@TypeConverters(GenreConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao
}