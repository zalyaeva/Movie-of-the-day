package com.android.movieoftheday.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieoftheday.data.MovieSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieSource: MovieSource
) : ViewModel() {

    fun loadTopRatedMovieList(){
        viewModelScope.launch {
            movieSource.getMovieList()
        }
    }
}