package com.msit.tmdb.core.module

import com.msit.tmdb.features.movies.presentation.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}