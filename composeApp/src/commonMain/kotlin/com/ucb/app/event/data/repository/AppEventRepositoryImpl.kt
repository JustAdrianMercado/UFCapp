package com.ucb.app.event.data.repository

import com.ucb.app.event.data.datasource.AppEventLocalDataSource
import com.ucb.app.event.data.datasource.AppEventRemoteDataSource
import com.ucb.app.event.domain.repository.AppEventRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AppEventRepositoryImpl(
    private val localDataSource: AppEventLocalDataSource,
    private val remoteDataSource: AppEventRemoteDataSource
) : AppEventRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun registerEvent(type: String) {
        val timestamp = localDataSource.currentTimeMillis()
        val eventId = localDataSource.saveEvent(type, timestamp)
        remoteDataSource.saveEvent(
            path = "app_events/$eventId",
            value = json.encodeToString(AppEventDto(type = type, timestamp = timestamp))
        )
        localDataSource.markAsSynced(eventId)
    }
}

@Serializable
private data class AppEventDto(
    val type: String,
    val timestamp: Long
)
