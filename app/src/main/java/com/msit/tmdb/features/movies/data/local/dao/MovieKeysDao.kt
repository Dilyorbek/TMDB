package com.msit.tmdb.features.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msit.tmdb.features.movies.data.local.entity.MovieKeys

@Dao
interface MovieKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<MovieKeys>)

    @Query("SELECT * FROM movie_keys ORDER BY id DESC")
    suspend fun getAll(): List<MovieKeys>

    @Query("SELECT * FROM movie_keys WHERE id = :id")
    suspend fun keysByMovieId(id: Int): MovieKeys?

    @Query("DELETE FROM movie_keys")
    suspend fun clear()

}