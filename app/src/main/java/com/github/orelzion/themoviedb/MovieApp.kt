package com.github.orelzion.themoviedb

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@MovieApp)
            modules(appModule)
        }
    }
}