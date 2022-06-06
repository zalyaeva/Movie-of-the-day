package com.android.movieoftheday.app.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.android.movieoftheday.model.base.NavigationItem
import com.android.movieoftheday.app.ui.screen.favorite.FavoriteScreen
import com.android.movieoftheday.app.ui.screen.home.HomeScreen
import com.android.movieoftheday.app.ui.screen.info.MovieInfoScreen
import com.android.movieoftheday.app.ui.screen.randommovie.RandomMovieScreen

@Composable
@ExperimentalPagingApi
@ExperimentalFoundationApi
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) { HomeScreen(navController = navController) }
        composable(NavigationItem.Favorites.route) { FavoriteScreen() }
        composable(NavigationItem.RandomMovie.route) { RandomMovieScreen() }

        composable(NavigationItem.MovieInfo.route) { MovieInfoScreen() }
    }
}