package com.android.movieoftheday.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val genres: List<Genre> = ArrayList(),
    val title: String = "",
    val overview: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    val runtime: Int = 0,
    val status: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("poster_path") private val posterPath: String? = null
){
    fun getImageUrl(): String? = if(!posterPath.isNullOrBlank()) "http://image.tmdb.org/t/p/w185$posterPath" else null
}
