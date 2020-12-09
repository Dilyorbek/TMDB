package com.msit.tmdb.features.movies.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MovieDao {

    @Query("SELECT * from movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * from movie order by popularity desc")
    fun getPopularMovies(): Flow<List<Movie>>

    @Query("SELECT * from movie WHERE id=:id")
    fun get(id: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Query("DELETE FROM movie WHERE id=:id")
    suspend fun delete(id: Int)

    fun getUntilChange(id: Int) = get(id).distinctUntilChanged()

}