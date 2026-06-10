package com.ucb.app.config.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class ConfigLocalDataSource {
    actual suspend fun saveConfig(key: String, value: String) {
        println("ConfigLocalDataSource iOS: saveConfig not implemented")
    }

    actual fun observeConfigValue(key: String): Flow<String?> {
        return flowOf(null)
    }

    actual suspend fun getConfigValue(key: String): String? {
        return null
    }
}
