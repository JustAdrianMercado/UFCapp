package com.ucb.app.fights.data.repository

import com.ucb.app.fights.data.mapper.toDomain
import com.ucb.app.fights.data.service.FightApiService
import com.ucb.app.fights.domain.model.Fight
import com.ucb.app.fights.domain.repository.FightRepository

class FightRepositoryImpl(
    private val apiService: FightApiService
) : FightRepository {

    override suspend fun getUpcomingFights(): List<Fight> {
        return try {
            val apiResponse = apiService.getUpcomingFights()
            
            // Si hay errores en la respuesta (como en tu código JS)
            if (apiResponse.errors != null && apiResponse.errors.toString() != "[]") {
                println("API ERROR DETECTED: ${apiResponse.errors}")
                return emptyList()
            }

            val fightsDto = apiResponse.response ?: emptyList()
            println("API DEBUG: Se encontraron ${fightsDto.size} peleas")
            
            fightsDto.map { it.toDomain() }
        } catch (e: Exception) {
            println("API REPO ERROR: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
}
