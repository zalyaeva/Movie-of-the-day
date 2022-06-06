package com.android.movieoftheday.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Genre(
    val id: Int,
    val name: String
)

class GenreConverter {
    @TypeConverter
    fun fromGenreList(list: List<Genre>?): String {
        if(list == null) return ""

        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toGenreList(genreList: String?): List<Genre>? {
        if (genreList == null) {
            return ArrayList()
        }

        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre>?>() {}.type
        return gson.fromJson(genreList, type)
    }
}
