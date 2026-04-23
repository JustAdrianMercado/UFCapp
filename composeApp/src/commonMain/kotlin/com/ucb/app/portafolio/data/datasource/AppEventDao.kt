package com.ucb.app.portafolio.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: AppEventEntity): Long

    @Query("UPDATE app_event SET synced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)

    @Query("SELECT * FROM app_event ORDER BY timestamp DESC")
    suspend fun getAllEvents(): List<AppEventEntity>
}