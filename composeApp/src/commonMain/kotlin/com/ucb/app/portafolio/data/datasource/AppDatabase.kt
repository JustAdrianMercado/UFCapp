package com.ucb.app.portafolio.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ConfigEntity::class,
        AppEventEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
    abstract fun appEventDao(): AppEventDao
}