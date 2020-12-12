package com.msit.tmdb.core.module

import android.app.Application
import com.msit.tmdb.core.data.local.AppDatabase
import org.koin.dsl.module


val databaseModule = module {

    fun provideDatabase(application: Application) = AppDatabase.create(application)

    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    single { provideDatabase(get()) }
    single { provideMovieDao(get()) }
}