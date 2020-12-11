package com.msit.tmdb

import android.app.Application
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.msit.tmdb.core.util.Constants
import com.msit.tmdb.core.data.local.AppDatabase
import com.msit.tmdb.core.data.remote.AuthInterceptor
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.movies.data.repository.MovieRepository
import com.msit.tmdb.features.movies.presentation.movies.MoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}

val repositoryModule = module {
    single { MovieRepository(get(), get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application) = AppDatabase.create(application)

    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    fun provideMovieKeysDao(database: AppDatabase) = database.movieKeysDao()

    single { provideDatabase(get()) }
    single { provideMovieDao(get()) }
    single { provideMovieKeysDao(get()) }
}


val networkModule = module {
    factory { get<Retrofit>().create(MovieService::class.java) }

    fun provideGson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()


    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message -> Log.d("HTTP:", message) }
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    fun provideAuthInterceptor() = AuthInterceptor()

    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ) = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    fun provideRetrofit(factory: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()


    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideAuthInterceptor() }
    single { provideHttpClient(get(), get()) }
    single { provideRetrofit(get(), get()) }
}

val appModules = listOf(networkModule, databaseModule, repositoryModule, viewModelModule)
