package com.ucb.app.ranking.domain.usecase

import com.ucb.app.ranking.domain.repository.RankingRepository

class GetRankingsUseCase(
    private val repository: RankingRepository
) {
    suspend operator fun invoke() = repository.getRankings()
}