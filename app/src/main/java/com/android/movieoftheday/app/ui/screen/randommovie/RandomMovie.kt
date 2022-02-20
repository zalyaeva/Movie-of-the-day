package com.android.movieoftheday.app.ui.screen.randommovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.android.movieoftheday.app.ui.theme.MovieOfTheDayTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


@Composable
fun RandomMovieScreen(
    state: MovieContract.State,
    effectFlow: Flow<MovieContract.Effect>?
) {
//    // Listen for side effects from the VM
//    LaunchedEffect(effectFlow) {
//        effectFlow?.onEach { effect ->
//            if (effect is MovieContract.Effect.DataLoaded){
//
//            }
//        }?.collect()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = state.movie?.title ?: "Random Movie View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }

    if (state.isLoading){
        LoadingBar()
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieOfTheDayTheme {
        RandomMovieScreen(MovieContract.State(), null)
    }
}