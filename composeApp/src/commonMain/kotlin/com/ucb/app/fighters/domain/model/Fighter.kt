package com.ucb.app.fighters.domain.model

data class Fighter(
    val id: String,
    val name: String,
    val nickname: String,
    val division: String,
    val record: String,
    val imageUrl: String
)