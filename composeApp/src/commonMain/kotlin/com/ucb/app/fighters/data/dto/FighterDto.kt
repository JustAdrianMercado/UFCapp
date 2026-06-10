package com.ucb.app.fighters.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FighterResponse(
    val response: List<FighterDto>? = null,
    val errors: JsonElement? = null
)

@Serializable
data class FighterDto(
    val id: Int,
    val name: String,
    val nickname: String? = null,
    val photo: String? = null,
    val gender: String? = null,
    val birthdate: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val reach: String? = null,
    val stance: String? = null,
    val team: JsonElement? = null,
    val lastUpdate: String? = null
)
