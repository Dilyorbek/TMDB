package com.msit.tmdb

import android.app.Application
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import com.msit.tmdb.core.module.*
import com.msit.tmdb.core.util.ProductionTree
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ModernApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(ProductionTree())

        startKoin {
            printLogger()
            androidContext(this@ModernApplication)
            modules(listOf(networkModule, databaseModule, repositoryModule, viewModelModule))
        }

    }
}