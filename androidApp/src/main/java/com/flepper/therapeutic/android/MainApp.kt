package com.flepper.therapeutic.android

import android.app.Application
import com.flepper.therapeutic.android.di.presentationModule
import com.flepper.therapeutic.di.netWorkingModule
import com.flepper.therapeutic.di.repositoryModule
import com.flepper.therapeutic.di.useCaseFactoryModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(this)
        //Initialise Koin for Dependency Injection

        startKoin {
            allowOverride(true)
            androidContext(this@MainApp)
            // if (BuildConfig.DEBUG)
            androidLogger(Level.ERROR)
            modules(appModules, netWorkingModule, repositoryModule, useCaseFactoryModule)
        }

    }


}

val appModules = presentationModule
