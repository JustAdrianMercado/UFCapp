package com.ucb.app.fights.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FightDto(
    val id: String = "",
    val fighter1: String = "",
    val fighter2: String = "",
    val eventName: String = "",
    val date: String = "",
    val imageUrl: String = ""
)