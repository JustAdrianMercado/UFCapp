package com.ucb.app.config.domain.usecase

import com.ucb.app.config.domain.repository.ConfigRepository

class SyncInitialConfigUseCase(
    private val repository: ConfigRepository
) {
    suspend operator fun invoke(key: String) {
        repository.syncInitialConfig(key)
    }
}