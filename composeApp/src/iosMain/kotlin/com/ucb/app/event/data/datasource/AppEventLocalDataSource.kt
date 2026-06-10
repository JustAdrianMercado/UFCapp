package com.ucb.app.event.data.datasource

import com.ucb.app.portafolio.data.datasource.AppEventDao

actual class AppEventLocalDataSource actual constructor(appEventDao: AppEventDao) {
    actual suspend fun saveEvent(type: String, timestamp: Long): Long {
        return 0L
    }

    actual suspend fun markAsSynced(id: Long) {
    }

    actual fun currentTimeMillis(): Long {
        return 0L
    }
}
