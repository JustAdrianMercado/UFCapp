package com.ucb.app.config.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    suspend fun syncInitialConfig(key: String)
    fun observeConfigValue(key: String): Flow<String?>
}