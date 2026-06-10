package com.ucb.app.database

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
