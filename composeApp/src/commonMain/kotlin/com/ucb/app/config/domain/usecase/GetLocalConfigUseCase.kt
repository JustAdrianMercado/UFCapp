package com.ucb.app.config.domain.usecase

import com.ucb.app.config.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow

class GetLocalConfigUseCase(
    private val repository: ConfigRepository
) {
    operator fun invoke(key: String): Flow<String?> {
        return repository.observeConfigValue(key)
    }
}