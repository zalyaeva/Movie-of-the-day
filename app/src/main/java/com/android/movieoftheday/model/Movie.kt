package com.android.movieoftheday.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val genres: List<Genre>,
    val title: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    val runtime: Int,
    val status: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
