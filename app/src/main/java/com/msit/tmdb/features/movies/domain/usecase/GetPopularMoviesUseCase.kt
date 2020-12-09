package com.msit.tmdb.features.movies.domain.usecase

import com.msit.tmdb.core.usecase.UseCase
import com.msit.tmdb.features.movies.data.repository.MovieRepository
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class GetPopularMoviesUseCase(private val repository: MovieRepository): UseCase<Int, List<Movie>>() {
    override operator fun invoke(parameters: Int) = repository.getPopularMovies(parameters).flowOn(Dispatchers.IO)
}