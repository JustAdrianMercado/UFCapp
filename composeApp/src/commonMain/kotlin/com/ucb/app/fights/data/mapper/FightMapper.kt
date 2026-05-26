package com.ucb.app.fights.data.mapper

import com.ucb.app.fights.data.datasource.FightEntity
import com.ucb.app.fights.data.dto.FightDto
import com.ucb.app.fights.domain.model.Fight

fun FightDto.toDomain(): Fight {
    return Fight(
        id = id,
        fighter1 = fighter1,
        fighter2 = fighter2,
        eventName = eventName,
        date = date,
        imageUrl = imageUrl
    )
}

fun FightEntity.toDomain(): Fight {
    return Fight(
        id = id,
        fighter1 = fighter1,
        fighter2 = fighter2,
        eventName = eventName,
        date = date,
        imageUrl = imageUrl
    )
}

fun Fight.toEntity(): FightEntity {
    return FightEntity(
        id = id,
        fighter1 = fighter1,
        fighter2 = fighter2,
        eventName = eventName,
        date = date,
        imageUrl = imageUrl
    )
}