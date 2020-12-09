package com.msit.tmdb.features.movies.data.repository

import androidx.paging.PagingSource
import com.msit.tmdb.features.movies.data.remote.dataSource.MovieRemoteDataSource
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie

private const val PIXABAY_STARTING_PAGE_INDEX = 1


//class PixaBayPagingSource(
//    private val remoteService: MovieRemoteDataSource
//) : PagingSource<Int, Movie>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
//        val position = params.key ?: PIXABAY_STARTING_PAGE_INDEX
//        return try {
//            val response = service.getPics(position)
//            val photos = response.photos
//            Log.d("Zivi", "Service -> getPhotos: ${photos.size}")
//            LoadResult.Page(
//                data = photos,
//                prevKey = if (position == PIXABAY_STARTING_PAGE_INDEX) null else position,
//                nextKey = if (photos.isEmpty()) null else position + 1
//
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//}