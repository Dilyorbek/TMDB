package com.msit.tmdb.features.movies.presentation.movies

import androidx.lifecycle.*
import com.msit.tmdb.core.util.fold
import com.msit.tmdb.features.movies.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Success(emptyList()))
    val uiState: StateFlow<MoviesUiState> = _uiState
    private var page = 1

    init {
        refresh()
    }


    fun refresh() {
        page = 1
        getPopularMovies()
    }

    fun loadMore() {
        if (_uiState.value != MoviesUiState.Loading) {
            page++
            getPopularMovies()
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            getPopularMoviesUseCase(page).collect {
                it.fold(
                    fl = { error ->
                        _uiState.value = MoviesUiState.Error(error)
                    },
                    fr = { movies ->
                        _uiState.value = MoviesUiState.Success(movies)
                    },
                )
            }
        }
    }
}
