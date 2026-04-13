package com.ucb.app.portafolio.domain.usecase

import com.ucb.app.portafolio.domain.repository.PortafolioRepository

class SavePortafolioDataUseCase(
    private val repository: PortafolioRepository
) {
    suspend operator fun invoke(path: String, value: String) {
        repository.saveData(path, value)
    }
}