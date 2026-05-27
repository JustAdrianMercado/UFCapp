package com.ucb.app.ranking.domain.model

data class FighterRanking(
    val id: String,
    val rank: Int,
    val fighterName: String,
    val division: String,
    val imageUrl: String,
    val wins: Int,
    val losses: Int
)