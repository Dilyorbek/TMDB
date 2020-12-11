package com.msit.tmdb.features.movies.presentation.movies

import com.msit.tmdb.features.movies.data.local.entity.Movie


sealed class MoviesUiState {
    data class Success(val movies: List<Movie>): MoviesUiState()
    data class Error(val error: Throwable): MoviesUiState()
    object Loading: MoviesUiState()
}