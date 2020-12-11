package com.msit.tmdb.features.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.msit.tmdb.core.util.Constants

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String,
    val popularity: Double,
) {
  fun getPosterUrl() =Constants.IMAGE_URL + posterPath
}