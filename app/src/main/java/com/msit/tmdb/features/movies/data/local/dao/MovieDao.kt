package com.msit.tmdb.features.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msit.tmdb.features.movies.data.local.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getAll(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clear()
}