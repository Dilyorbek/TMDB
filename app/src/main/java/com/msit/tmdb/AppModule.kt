package com.msit.tmdb

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.msit.tmdb.core.util.Constants
import com.msit.tmdb.core.data.local.AppDatabase
import com.msit.tmdb.core.data.remote.AuthInterceptor
import com.msit.tmdb.features.movies.data.local.dao.MovieDao
import com.msit.tmdb.features.movies.data.local.dataSource.MovieLocalDataSource
import com.msit.tmdb.features.movies.data.remote.dataSource.MovieRemoteDataSource
import com.msit.tmdb.features.movies.data.remote.service.MovieService
import com.msit.tmdb.features.movies.data.repository.MovieRepository
import com.msit.tmdb.features.movies.domain.usecase.GetPopularMoviesUseCase
import com.msit.tmdb.features.movies.presentation.movies.MoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}

val useCaseModule = module {
    single { GetPopularMoviesUseCase(get()) }
}

val repositoryModule = module {
    single { MovieRepository(get(), get()) }
}

val apiModule = module {
    factory { get<Retrofit>().create(MovieService::class.java) }
    single { MovieRemoteDataSource(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
    single { MovieLocalDataSource(get()) }
}


val networkModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging =
            HttpLoggingInterceptor { message ->
                Log.d("HTTP:",message)
            }
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    fun provideAuthInterceptor() = AuthInterceptor()

    fun provideX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }
        }
    }

    fun providesSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf(trustManager)

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        trustManager: X509TrustManager,
        sslSocketFactory: SSLSocketFactory
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .sslSocketFactory(sslSocketFactory, trustManager)
            .build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideAuthInterceptor() }
    single { provideX509TrustManager() }
    single { providesSSLSocketFactory(get()) }
    single { provideHttpClient(get(), get(), get(), get()) }
    single { provideRetrofit(get(), get()) }
}

val appModules = listOf(networkModule, apiModule, databaseModule, repositoryModule, useCaseModule, viewModelModule)
