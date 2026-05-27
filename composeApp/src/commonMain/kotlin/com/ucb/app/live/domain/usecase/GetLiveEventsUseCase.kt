package com.ucb.app.live.domain.usecase

import com.ucb.app.live.domain.repository.LiveRepository

class GetLiveEventsUseCase(
    private val repository: LiveRepository
) {
    suspend operator fun invoke() = repository.getLiveEvents()
}