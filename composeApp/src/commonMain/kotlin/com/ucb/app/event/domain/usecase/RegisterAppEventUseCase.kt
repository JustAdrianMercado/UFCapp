package com.ucb.app.event.domain.usecase

import com.ucb.app.event.domain.repository.AppEventRepository

class RegisterAppEventUseCase(
    private val repository: AppEventRepository
) {
    suspend operator fun invoke(type: String) {
        repository.registerEvent(type)
    }
}