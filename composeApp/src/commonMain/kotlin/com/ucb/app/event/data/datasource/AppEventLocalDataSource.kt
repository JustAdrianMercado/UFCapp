package com.ucb.app.event.data.datasource

import com.ucb.app.database.AppEventDao

expect class AppEventLocalDataSource(appEventDao: AppEventDao) {
    suspend fun saveEvent(type: String, timestamp: Long): Long
    suspend fun markAsSynced(id: Long)
    fun currentTimeMillis(): Long
}
