package com.ucb.app.fighters.domain.usecase

import com.ucb.app.fighters.domain.repository.FighterRepository

class GetFightersUseCase(
    private val repository: FighterRepository
) {
    suspend operator fun invoke() = repository.getFighters()
}