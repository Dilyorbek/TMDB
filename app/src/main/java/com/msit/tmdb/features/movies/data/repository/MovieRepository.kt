package com.msit.tmdb.features.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.msit.tmdb.core.data.local.AppDatabase
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.movies.data.local.entity.Movie
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val database: AppDatabase,
    private val service: MovieService
) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
        private val TAG = MovieRepository::class.java.name
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),

            pagingSourceFactory = { MoviePagingSource(service) }
        ).flow
    }
}