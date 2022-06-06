package com.android.movieoftheday.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.android.movieoftheday.data.MovieSource
import com.android.movieoftheday.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getTopRatedMovieList() = repository.getMovieList()
}