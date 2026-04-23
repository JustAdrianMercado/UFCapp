package com.ucb.app.config.data.repository

import com.ucb.app.config.data.datasource.ConfigLocalDataSource
import com.ucb.app.config.data.datasource.RemoteConfigDataSource
import com.ucb.app.config.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow

class ConfigRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource,
    private val localDataSource: ConfigLocalDataSource
) : ConfigRepository {

    override suspend fun syncInitialConfig(key: String) {
        try {
            val remoteValue = remoteConfigDataSource.fetchValue(key)
            if (remoteValue.isNotBlank()) {
                localDataSource.saveConfig(key, remoteValue)
            }
        } catch (_: Exception) {
            // falla internet → usar cache local
        }
    }

    override fun observeConfigValue(key: String): Flow<String?> {
        return localDataSource.observeConfigValue(key)
    }
}