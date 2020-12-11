package com.msit.tmdb.features.movies.data.remote.service

import com.msit.tmdb.core.data.remote.ApiResponse
import com.msit.tmdb.features.movies.data.remote.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesResponse
}