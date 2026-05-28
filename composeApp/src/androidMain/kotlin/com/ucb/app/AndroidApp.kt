package com.ucb.app

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ucb.app.di.androidConfigModule
import com.ucb.app.di.androidEventModule
import com.ucb.app.di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AndroidApp)
            modules(getModules() + androidConfigModule + androidEventModule)
        }
    }
}