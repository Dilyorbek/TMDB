package com.msit.tmdb.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.msit.tmdb.core.util.Constants
import com.msit.tmdb.features.movies.data.local.dao.MovieDao
import com.msit.tmdb.features.movies.data.local.entity.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .build()
    }

    abstract fun movieDao(): MovieDao
}