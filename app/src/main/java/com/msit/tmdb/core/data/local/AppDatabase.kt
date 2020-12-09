package com.msit.tmdb.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msit.tmdb.features.movies.data.local.dao.MovieDao
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "movie_database"
    }
    abstract fun movieDao(): MovieDao
}