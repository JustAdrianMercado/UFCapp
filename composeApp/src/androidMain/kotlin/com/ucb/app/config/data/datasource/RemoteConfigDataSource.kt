package com.ucb.app.config.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

actual class RemoteConfigDataSource {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(settings)
    }

    actual suspend fun fetchValue(key: String): String {
        remoteConfig.fetchAndActivate().await()
        return remoteConfig.getString(key)
    }
}