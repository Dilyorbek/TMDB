package com.msit.tmdb.features.movies.data.remote.response

import com.google.gson.annotations.SerializedName
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie

data class MoviesResponse(
    val results: List<Movie>,
    @SerializedName("total_results") val itemCount: Int,
    val page: String,
    @SerializedName("total_pages") val pageCount: Int
)