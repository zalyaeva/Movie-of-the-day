package com.android.movieoftheday.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import com.android.movieoftheday.model.NavigationItem
import com.android.movieoftheday.app.ui.screen.favorite.FavoriteScreen
import com.android.movieoftheday.app.ui.screen.home.HomeScreen
import com.android.movieoftheday.app.ui.screen.randommovie.RandomMovieScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Favorites.route) { FavoriteScreen() }
        composable(NavigationItem.RandomMovie.route) { RandomMovieScreen() }
    }
}