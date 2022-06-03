package com.android.movieoftheday.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.android.movieoftheday.R

@Composable
fun CreateRating(rating: Double){
    Row(
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small))
    ) {
        Text(
            text = stringResource(id = R.string.text_rating),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.small)),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onBackground,
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
fun CreateDescriptionMovie(description: String?){
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
        fontSize = 25.sp
    )
}