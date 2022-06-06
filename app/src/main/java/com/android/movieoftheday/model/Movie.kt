package com.android.movieoftheday.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top_rated_movie_database")
data class Movie(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val genres: List<Genre>? = ArrayList(),
    val title: String? = "",
    val overview: String? = "",
    @SerializedName("release_date") val releaseDate: String = "",
    val status: String? = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("poster_path") val posterPath: String? = null
) {

    fun getImageUrl(): String? =
        if (!posterPath.isNullOrBlank()) "https://image.tmdb.org/t/p/w185$posterPath" else null
}
