package com.ucb.app.portafolio.data.repository

import com.ucb.app.portafolio.data.datasource.FirebaseManager
import com.ucb.app.portafolio.domain.repository.PortafolioRepository

class PortafolioRepositoryImpl(
    private val firebaseManager: FirebaseManager
) : PortafolioRepository {

    override suspend fun saveData(path: String, value: String) {
        firebaseManager.saveData(path, value)
    }
}