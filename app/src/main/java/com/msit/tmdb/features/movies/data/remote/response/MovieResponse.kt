package com.msit.tmdb.features.movies.data.remote.response

import com.google.gson.annotations.SerializedName
import com.msit.tmdb.features.movies.data.local.entity.Movie

data class MoviesResponse(
    val results: List<Movie>,
    @SerializedName("total_results") val itemCount: Int,
    val page: Int,
    @SerializedName("total_pages") val pageCount: Int
)