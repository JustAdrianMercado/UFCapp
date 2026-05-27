package com.ucb.app.ranking.domain.repository

import com.ucb.app.ranking.domain.model.FighterRanking

interface RankingRepository {
    suspend fun getRankings(): List<FighterRanking>
}