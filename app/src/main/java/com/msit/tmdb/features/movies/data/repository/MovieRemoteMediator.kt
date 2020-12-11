package com.msit.tmdb.features.movies.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.msit.tmdb.core.data.local.AppDatabase
import com.msit.tmdb.features.movies.data.local.entity.Movie
import com.msit.tmdb.features.movies.data.local.entity.MovieKeys
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieService: MovieService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getMovieKeyForLastItem(state) ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.next
                }
            }?: STARTING_PAGE_INDEX

            val response = movieService.getPopularMovies(page)
            val movies = response.results
            val endOfPaginationReached = response.page == response.pageCount
            if (movies != null) {
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        appDatabase.movieKeysDao().clear()
                        appDatabase.movieDao().clear()
                    }

                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = movies.map {
                        MovieKeys(id = it.id, prev = prevKey, next = nextKey)
                    }
                    appDatabase.movieKeysDao().insert(keys)
                    appDatabase.movieDao().insert(movies)
                }

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getMovieKeyForLastItem(state: PagingState<Int, Movie>): MovieKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                appDatabase.movieKeysDao().keysByMovieId(repo.id)
            }
    }

    private suspend fun getMovieKeys(): MovieKeys? {
        return appDatabase.movieKeysDao().getAll().firstOrNull()

    }
}