package com.ucb.app.fights.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fights")
data class FightEntity(
    @PrimaryKey val id: String,
    val fighter1: String,
    val fighter2: String,
    val eventName: String,
    val date: String,
    val imageUrl: String
)