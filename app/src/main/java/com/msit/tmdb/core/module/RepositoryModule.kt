package com.msit.tmdb.core.module

import com.msit.tmdb.features.movies.data.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get(), get()) }
}