package com.android.movieoftheday.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.android.movieoftheday.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.movieoftheday.model.NavigationItem
import com.android.movieoftheday.app.ui.theme.MovieOfTheDayTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@Composable
fun MainScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val navController = rememberNavController()
    MovieOfTheDayTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) {
            Navigation(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(NavigationItem.Home, NavigationItem.Favorites, NavigationItem.RandomMovie)
    BottomNavigation(
        modifier = Modifier.graphicsLayer {
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            clip = true
        },
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
                selectedContentColor = colorResource(id = R.color.accentViolet),
                unselectedContentColor = colorResource(id = R.color.primaryViolet),
                alwaysShowLabel = true,
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }else{
                        if(currentRoute == NavigationItem.RandomMovie.route){
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
