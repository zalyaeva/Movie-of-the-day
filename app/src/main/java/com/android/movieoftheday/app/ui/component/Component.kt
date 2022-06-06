package com.android.movieoftheday.app.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.android.movieoftheday.R
import com.android.movieoftheday.model.Movie
import com.android.movieoftheday.model.base.NavigationItem

@Composable
fun CreateRating(rating: Double, ratingModifier: Modifier? = null, color: Color? = null) {
    Row(
        modifier = ratingModifier ?: Modifier.padding(top = dimensionResource(id = R.dimen.small))
    ) {
        Text(
            text = stringResource(id = R.string.text_rating),
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.small)),
            textAlign = TextAlign.Start,
            color = color ?: MaterialTheme.colors.onBackground,
            fontSize = 14.sp
        )

        Text(
            text = rating.toString(),
            textAlign = TextAlign.Start,
            color = when {
                rating >= 7.5 -> colorResource(id = R.color.ratingGood)
                rating > 5 -> colorResource(id = R.color.ratingNormal)
                else -> colorResource(id = R.color.ratingBad)
            },
            fontSize = 14.sp
        )
    }
}

@Composable
fun CreateDescriptionMovie(description: String?) {
    Text(
        text = description ?: "",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .alpha(if (!description.isNullOrBlank()) 1f else 0f)
            .padding(vertical = dimensionResource(id = R.dimen.small)),
        textAlign = TextAlign.Start,
        fontSize = 14.sp
    )
}

@Composable
fun CreateNameMovie(title: String?, modifier: Modifier? = null) {
    Text(
        text = title ?: "Random movie's name",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier ?: Modifier.padding(vertical = dimensionResource(id = R.dimen.small)),
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )
}

@ExperimentalFoundationApi
@Composable
fun CreateMovieList(list: LazyPagingItems<Movie>, navController: NavController?) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.small)),
        content = {
            items(count = list.itemCount) { index ->
                list[index]?.let {
                    CreateSmallMovieCard(movie = it, navController = navController)
                }
            }
        }
    )
}

@Composable
fun CreateSmallMovieCard(movie: Movie?, navController: NavController?) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movie?.getImageUrl())
            .placeholder(drawableResId = R.drawable.ic_movie_error_load)
            .error(drawableResId = R.drawable.ic_movie_error_load)
            .build()
    )

    Card(
        modifier = Modifier
            .clickable {
                navController?.navigate(NavigationItem.MovieInfo.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                painter = painter,
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop
            )

            Text(
                text = movie?.title.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.small)
                    ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            CreateRating(
                rating = movie?.voteAverage ?: 0.0,
                ratingModifier = Modifier.padding(dimensionResource(id = R.dimen.standard)),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}