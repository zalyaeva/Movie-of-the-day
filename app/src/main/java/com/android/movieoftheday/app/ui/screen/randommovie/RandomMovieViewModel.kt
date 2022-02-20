package com.android.movieoftheday.app.ui.screen.randommovie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieoftheday.data.MovieSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomMovieViewModel @Inject constructor(
    private val movieSource: MovieSource
) : ViewModel() {

    var state by mutableStateOf(MovieContract.State(movie = null, isLoading = true))
        private set

    var effects = Channel<MovieContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch { getFoodCategories() }
    }

    private suspend fun getFoodCategories() {
        val randomMovie = movieSource.getRandomMovie()
        viewModelScope.launch {
            state = state.copy(movie = randomMovie, isLoading = false)
            effects.send(MovieContract.Effect.DataLoaded)
        }
    }
}