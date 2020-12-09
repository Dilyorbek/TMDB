package com.msit.tmdb.features.movies.data.remote.dataSource

import android.util.Log
import com.google.gson.Gson
import com.msit.tmdb.core.data.remote.ApiError
import com.msit.tmdb.core.error.ServerException
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class MovieRemoteDataSource(val service: MovieService) {
    val TAG = MovieRemoteDataSource::class.java.name
    suspend fun getPopularMovies(page: Int): List<Movie> {
        try {
            val response = service.getPopularMovies(page)

            Log.e(TAG, response.toString())
            if (response.isSuccessful) {
                return response.body()?.results ?: emptyList()
            } else {
                var errorMessage: ApiError? = null

                response.errorBody()?.let {
                    errorMessage = Gson().fromJson(it.string(), ApiError::class.java)
                    Log.e(TAG, "ErrorBody = ${errorMessage.toString()}")
                }

                throw ServerException()
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            throw ServerException()
        }
    }
}