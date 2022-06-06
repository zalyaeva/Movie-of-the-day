package com.android.movieoftheday.app.ui.screen.randommovie

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.movieoftheday.R
import com.android.movieoftheday.model.base.Result
import com.android.movieoftheday.app.ui.component.CreateDescriptionMovie
import com.android.movieoftheday.app.ui.component.CreateNameMovie
import com.android.movieoftheday.app.ui.component.CreateRating
import com.android.movieoftheday.app.ui.theme.MovieOfTheDayTheme
import com.android.movieoftheday.data.observe
import com.android.movieoftheday.model.Movie

@Composable
fun RandomMovieScreen(viewModel: RandomMovieViewModel = hiltViewModel()) {
    OnLifecycleEvent { _, event ->
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
            if (data.data != null) {
                ShowRandomMovie(data.data)
            } else {
                ShowRetryButton(viewModel = viewModel)
            }
        }
        is Result.Failure -> {
            ShowErrorDialog(visible = visibleDialog, message = data.errorString)
            if (!visibleDialog.value) {
                ShowRetryButton(viewModel = viewModel)
            }
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
            .padding(dimensionResource(id = R.dimen.big))
            .background(MaterialTheme.colors.background)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.getImageUrl())
                    .build(),
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_movie_error_load),
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            )

            CreateNameMovie(
                title = movie.title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = dimensionResource(id = R.dimen.small))
            )
            if ((movie.voteAverage != null)) {
                CreateRating(rating = movie.voteAverage)
            }
            CreateDescriptionMovie(description = movie.overview)
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
fun ShowRetryButton(viewModel: RandomMovieViewModel?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { viewModel?.refresh() },
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            Text(
                text = stringResource(id = R.string.error_retry),
                fontSize = 16.sp,
                color = MaterialTheme.colors.secondary
            )
        }
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
                posterPath = "w1280/kMDBYLPGpInOJWb29SDB8Du8Pey.jpg",
                voteAverage = 5.4
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

@Preview(showBackground = true)
@Composable
fun ShowRetryButtonPreview() {
    MovieOfTheDayTheme {
        ShowRetryButton(viewModel = null)
    }
}