package com.ucb.app.event.data.datasource

import com.ucb.app.portafolio.data.datasource.AppEventDao
import com.ucb.app.portafolio.data.datasource.AppEventEntity

actual class AppEventLocalDataSource actual constructor(
    private val appEventDao: AppEventDao
) {

    actual suspend fun saveEvent(type: String, timestamp: Long): Long {
        return appEventDao.insertEvent(
            AppEventEntity(
                type = type,
                timestamp = timestamp,
                synced = false
            )
        )
    }

    actual suspend fun markAsSynced(id: Long) {
        appEventDao.markAsSynced(id)
    }

    actual fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}