package com.msit.tmdb.features.movies.data.local.dataSource

import com.msit.tmdb.core.error.CacheException
import com.msit.tmdb.core.error.ServerException
import com.msit.tmdb.features.movies.data.local.dao.MovieDao
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.flow.flow

class MovieLocalDataSource(val dao: MovieDao) {
    suspend fun saveMovies(movies: List<Movie>) {
        dao.insert(movies)
    }

    fun getMovies() = dao.getAll()

    fun getPopularMovies() = dao.getPopularMovies()

    suspend fun deleteMovies() = dao.deleteAll()
}