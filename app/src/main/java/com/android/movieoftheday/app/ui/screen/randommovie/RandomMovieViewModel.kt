package com.android.movieoftheday.app.ui.screen.randommovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieoftheday.model.base.Result
import com.android.movieoftheday.data.MovieSource
import com.android.movieoftheday.model.base.ErrorResponse
import com.android.movieoftheday.model.Movie
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class RandomMovieViewModel @Inject constructor(
    private val movieSource: MovieSource
) : ViewModel() {

    private val _randomMovie = MutableLiveData<Result<Movie>>()
    val randomMovie: LiveData<Result<Movie>> = _randomMovie

    private suspend fun subscribeLifecycle() {
        getFoodCategories()
    }

    private suspend fun getFoodCategories() {
        _randomMovie.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                _randomMovie.postValue(movieSource.getRandomMovie())
            } catch (exception: Exception) {
                val errorMessage = if (exception is HttpException) {
                    val errorResponse: ErrorResponse =
                        Gson().fromJson(
                            exception.response()?.errorBody()?.charStream(),
                            ErrorResponse::class.java
                        )
                    errorResponse.statusMessage
                } else {
                    "Неизвестная ошибка"
                }

                _randomMovie.postValue(Result.Failure(errorString = errorMessage))
            }
        }
    }

    fun refresh() {
        viewModelScope.launch { subscribeLifecycle() }
    }
}