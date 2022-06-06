package com.android.movieoftheday.app.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.movieoftheday.app.ui.component.CreateMovieList
import com.android.movieoftheday.app.ui.component.CreateSmallMovieCard
import com.android.movieoftheday.app.ui.theme.MovieOfTheDayTheme
import com.android.movieoftheday.model.Movie

@ExperimentalFoundationApi
@Composable
@ExperimentalPagingApi
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController?) {
    val topRatedMovieList = viewModel.getTopRatedMovieList().collectAsLazyPagingItems()
    CreateMovieList(
        list = topRatedMovieList, navController = navController
    )
}

@Preview(showBackground = true)
@Composable
@ExperimentalPagingApi
@ExperimentalFoundationApi
fun HomeScreenPreview() {
    HomeScreen(hiltViewModel(), null)
}

@Composable
@Preview(showBackground = true)
fun ItemMoviePreview() {
    MovieOfTheDayTheme {
        CreateSmallMovieCard(
            movie = Movie(
                0,
                title = "Социальная сеть",
                overview = "Лучший фильм Джесси Айзенберга, который сыграл Марка Цукерберга.",
                posterPath = "w1280/kMDBYLPGpInOJWb29SDB8Du8Pey.jpg",
                voteAverage = 5.4
            ), navController = null
        )
    }
}