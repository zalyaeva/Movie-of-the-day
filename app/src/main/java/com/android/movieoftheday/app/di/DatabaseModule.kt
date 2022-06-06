package com.android.movieoftheday.app.di

import android.content.Context
import androidx.room.Room
import com.android.movieoftheday.data.repository.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}