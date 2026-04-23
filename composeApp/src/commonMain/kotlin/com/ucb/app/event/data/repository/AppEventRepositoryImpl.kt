package com.ucb.app.event.data.repository

import com.ucb.app.event.data.datasource.AppEventLocalDataSource
import com.ucb.app.event.data.datasource.AppEventRemoteDataSource
import com.ucb.app.event.domain.repository.AppEventRepository

class AppEventRepositoryImpl(
    private val localDataSource: AppEventLocalDataSource,
    private val remoteDataSource: AppEventRemoteDataSource
) : AppEventRepository {

    override suspend fun registerEvent(type: String) {
        val timestamp = localDataSource.currentTimeMillis()

        val localId = localDataSource.saveEvent(
            type = type,
            timestamp = timestamp
        )

        try {
            val path = "app_events/$timestamp"
            val value = "$type|$timestamp"

            remoteDataSource.saveEvent(path, value)
            localDataSource.markAsSynced(localId)
        } catch (_: Exception) {
        }
    }
}