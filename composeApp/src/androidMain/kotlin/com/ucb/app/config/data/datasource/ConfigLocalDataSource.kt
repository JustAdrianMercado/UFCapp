package com.ucb.app.config.data.datasource

import com.ucb.app.portafolio.data.datasource.ConfigDao
import com.ucb.app.portafolio.data.datasource.ConfigEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

actual class ConfigLocalDataSource(
    private val configDao: ConfigDao
) {

    actual suspend fun saveConfig(key: String, value: String) {
        val entity = ConfigEntity(
            key = key,
            value = value,
            updatedAt = System.currentTimeMillis()
        )
        configDao.insertConfig(entity)
    }

    actual fun observeConfigValue(key: String): Flow<String?> {
        return configDao.observeConfig(key).map { it?.value }
    }

    actual suspend fun getConfigValue(key: String): String? {
        return configDao.getConfig(key)?.value
    }
}