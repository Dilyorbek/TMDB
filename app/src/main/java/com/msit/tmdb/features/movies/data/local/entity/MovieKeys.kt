package com.msit.tmdb.features.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_keys")
data class MovieKeys(
    @PrimaryKey
    val id: Int,
    val prev: Int?,
    val next: Int?,
)