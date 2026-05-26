package com.ucb.app.fights.domain.usecase

import com.ucb.app.fights.domain.repository.FightRepository

class GetUpcomingFightsUseCase(
    private val repository: FightRepository
) {
    suspend operator fun invoke() = repository.getUpcomingFights()
}