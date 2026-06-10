package com.ucb.app.fighters.data.repository

import com.ucb.app.fighters.data.mapper.toDomain
import com.ucb.app.fighters.data.service.FighterApiService
import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.domain.repository.FighterRepository

class FighterRepositoryImpl(
    private val apiService: FighterApiService
) : FighterRepository {
    override suspend fun getFighters(search: String?): List<Fighter> {
        return try {
            val response = apiService.getFighters(search)
            val fightersDto = response.response ?: emptyList()
            fightersDto.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
