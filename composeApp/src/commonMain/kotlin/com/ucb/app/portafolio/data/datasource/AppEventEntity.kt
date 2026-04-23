package com.ucb.app.portafolio.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_event")
data class AppEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val timestamp: Long,
    val synced: Boolean = false
)