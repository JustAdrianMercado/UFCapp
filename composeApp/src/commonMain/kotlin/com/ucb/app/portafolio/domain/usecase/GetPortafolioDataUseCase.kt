package com.ucb.app.portafolio.domain.usecase

import com.ucb.app.portafolio.domain.repository.PortafolioRepository

class GetPortafolioDataUseCase(
    private val repository: PortafolioRepository
) {
    suspend operator fun invoke(path: String): String? {
        return repository.getData(path)
    }
}