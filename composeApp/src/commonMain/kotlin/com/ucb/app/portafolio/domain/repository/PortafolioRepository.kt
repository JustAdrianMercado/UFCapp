package com.ucb.app.portafolio.domain.repository

interface PortafolioRepository {
    suspend fun saveData(path: String, value: String)
}