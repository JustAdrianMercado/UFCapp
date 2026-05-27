package com.ucb.app.ranking.data.repository

import com.ucb.app.ranking.domain.model.FighterRanking
import com.ucb.app.ranking.domain.repository.RankingRepository

class RankingRepositoryImpl : RankingRepository {

    override suspend fun getRankings(): List<FighterRanking> {
        return listOf(
            FighterRanking(
                id = "1",
                rank = 1,
                fighterName = "Islam Makhachev",
                division = "Lightweight",
                imageUrl = "https://via.placeholder.com/300",
                wins = 27,
                losses = 1
            ),
            FighterRanking(
                id = "2",
                rank = 2,
                fighterName = "Leon Edwards",
                division = "Welterweight",
                imageUrl = "https://via.placeholder.com/300",
                wins = 22,
                losses = 3
            ),
            FighterRanking(
                id = "3",
                rank = 3,
                fighterName = "Alex Pereira",
                division = "Light Heavyweight",
                imageUrl = "https://via.placeholder.com/300",
                wins = 10,
                losses = 2
            )
        )
    }
}