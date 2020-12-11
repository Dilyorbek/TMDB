package com.msit.tmdb.features.movies.presentation.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.msit.tmdb.features.movies.data.repository.MovieRepository
import com.msit.tmdb.features.movies.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {

    fun fetchMovies(): Flow<PagingData<Movie>> {
        return repository.getMovies().cachedIn(viewModelScope)
    }
}
