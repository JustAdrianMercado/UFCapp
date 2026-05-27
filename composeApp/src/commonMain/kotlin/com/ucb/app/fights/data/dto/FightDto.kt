package com.ucb.app.fights.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MmaResponse(
    val response: List<FightDto>? = null,
    val errors: JsonElement? = null // Capturamos errores si los hay
)

@Serializable
data class FightDto(
    val id: Int,
    val date: String,
    val slug: String? = null,
    val fighters: FightersDto
)

@Serializable
data class FightersDto(
    val first: FighterDetailDto,
    val second: FighterDetailDto
)

@Serializable
data class FighterDetailDto(
    val name: String,
    val logo: String? = null
)
