package com.android.movieoftheday.app.ui.screen.randommovie

import com.android.movieoftheday.model.Movie

class MovieContract {
    data class State(
        val movie: Movie? = null,
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataLoaded : Effect()
    }
}