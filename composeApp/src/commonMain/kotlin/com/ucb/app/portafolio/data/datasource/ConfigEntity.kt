package com.ucb.app.portafolio.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class ConfigEntity(
    @PrimaryKey
    val key: String,
    val value: String,
    val updatedAt: Long
)