package com.ucb.app.fighters.data.mapper

import com.ucb.app.fighters.data.dto.FighterDto
import com.ucb.app.fighters.domain.model.Fighter
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun FighterDto.toDomain(): Fighter {
    val teamName = try {
        team?.jsonObject?.get("name")?.jsonPrimitive?.content ?: "No Team"
    } catch (e: Exception) {
        "No Team"
    }

    return Fighter(
        id = id.toString(),
        name = name,
        nickname = nickname ?: "",
        division = weight ?: "Unknown",
        record = teamName, // Usamos el nombre del equipo aquí si no hay record
        imageUrl = photo ?: ""
    )
}
