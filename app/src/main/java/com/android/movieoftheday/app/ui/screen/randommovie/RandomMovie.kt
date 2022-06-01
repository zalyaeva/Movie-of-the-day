package com.android.movieoftheday.app.ui.screen.randommovie

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.movieoftheday.R
import com.android.movieoftheday.app.retrofit.Result
import com.android.movieoftheday.app.ui.theme.MovieOfTheDayTheme
import com.android.movieoftheday.data.observe
import com.android.movieoftheday.model.Movie

@Composable
fun RandomMovieScreen(viewModel: RandomMovieViewModel = hiltViewModel()) {
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.refresh()
            }
            else -> {}
        }
    }

    val visibleDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    when (val data = observe(data = viewModel.randomMovie)) {
        is Result.Loading -> {
            LoadingBar()
        }
        is Result.Success -> {
            ShowRandomMovie(data.data ?: Movie(0))
        }
        is Result.Failure -> {
            ShowErrorDialog(visible = visibleDialog, message = data.errorString)
//                onRetry = { viewModel.loadData(topAppBarMenu.showRandomDashboard) })
        }
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ShowRandomMovie(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("http://image.tmdb.org/t/p/w185/qNVJwG2k7GlvE9t0DQOPsy5aZL5.jpg")
                    .error(drawableResId = R.drawable.ic_movie_error_load)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .align(Alignment.CenterHorizontally)
                    .padding(dimensionResource(id = R.dimen.big))
            )

            Text(
                text = movie.title ?: "Random movie's name",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.small),
                        horizontal = dimensionResource(id = R.dimen.standard)
                    ),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )

            val overviewVisible = if (movie.overview.isNotBlank()) 1f else 0f
            Text(
                text = movie.overview ?: "",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .alpha(overviewVisible)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.small),
                        horizontal = dimensionResource(id = R.dimen.standard)
                    ),
                textAlign = TextAlign.Start,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ShowErrorDialog(visible: MutableState<Boolean>, message: String?) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(stringResource(R.string.error)) },
            confirmButton = {
                Button(
                    onClick = { visible.value = false },
                ) {
                    Text("Ok")
                }
            },
            text = { Text(message ?: stringResource(R.string.error_description)) },
        )
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
fun RandomMoviePreview() {
    MovieOfTheDayTheme {
        ShowRandomMovie(
            Movie(
                0,
                title = "Социальная сеть",
                overview = "Лучший фильм Джесси Айзенберга, который сыграл Марка Цукерберга.",
                posterPath = "w1280/kMDBYLPGpInOJWb29SDB8Du8Pey.jpg"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieOfTheDayTheme {
        RandomMovieScreen()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ShowErrorDialogPreview() {
    MovieOfTheDayTheme {
        ShowErrorDialog(visible = mutableStateOf(true), message = "Неизвестная ошибка")
    }
}