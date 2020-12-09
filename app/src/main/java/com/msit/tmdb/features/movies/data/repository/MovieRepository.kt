package com.msit.tmdb.features.movies.data.repository

import com.msit.tmdb.core.util.*
import com.msit.tmdb.features.movies.data.local.dataSource.MovieLocalDataSource
import com.msit.tmdb.features.movies.data.remote.dataSource.MovieRemoteDataSource
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception

class MovieRepository(
    val localDataSource: MovieLocalDataSource,
    val remoteDataSource: MovieRemoteDataSource
) {

    fun getPopularMovies(param: Int): Flow<Either<Exception, List<Movie>>> = flow {

        try {
            if (param==1) {
                localDataSource.deleteMovies()
            }

            emit(Right(localDataSource.getPopularMovies().first()))

            val result: List<Movie> = remoteDataSource.getPopularMovies(param)

            localDataSource.saveMovies(result)

            emitAll(localDataSource.getPopularMovies().map {
                Right(it)
            })

        } catch (e: Exception) {
            emit(Left(e))
        }
    }.flowOn(Dispatchers.IO)
}