package com.ucb.app.config.data.datasource

import kotlinx.coroutines.flow.Flow

expect class ConfigLocalDataSource {
    suspend fun saveConfig(key: String, value: String)
    fun observeConfigValue(key: String): Flow<String?>
    suspend fun getConfigValue(key: String): String?
}