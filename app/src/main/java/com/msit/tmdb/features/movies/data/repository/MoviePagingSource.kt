package com.msit.tmdb.features.movies.data.repository

import androidx.paging.PagingSource
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.movies.data.local.entity.Movie
import java.io.IOException


class MoviePagingSource(private val service: MovieService) :
    PagingSource<Int, Movie>() {

    companion object{
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = service.getPopularMovies(page = position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: retrofit2.HttpException) {
            return LoadResult.Error(exception)
        }
    }
}