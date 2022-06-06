package com.android.movieoftheday.model.base

import com.android.movieoftheday.R

sealed class NavigationItem(val route: String, val icon: Int?, val title: String) {
    object Home : NavigationItem("home", R.drawable.ic_movie, "Home")
    object Favorites : NavigationItem("favorites", R.drawable.ic_favorite, "Favorites")
    object RandomMovie : NavigationItem("randomMovie", R.drawable.ic_random, "RandomMovie")

    object MovieInfo: NavigationItem("movieInfo", null, "MovieInfo")
}
