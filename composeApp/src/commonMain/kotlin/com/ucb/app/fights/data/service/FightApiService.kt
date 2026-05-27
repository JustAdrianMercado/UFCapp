package com.ucb.app.fights.data.service

import com.ucb.app.fights.data.dto.MmaResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FightApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private val apiKey = "eb0a9257dfabfcf2a57667fc35922c6e"
    private val baseUrl = "https://v1.mma.api-sports.io"

    suspend fun getUpcomingFights(): MmaResponse {
        return try {
            println("DEBUG API: Llamando a $baseUrl/fights con fecha 2026-05-30")
            val response: MmaResponse = client.get("$baseUrl/fights") {
                header("x-apisports-key", apiKey)
                parameter("date", "2026-05-30")
            }.body()
            
            println("DEBUG API: Respuesta recibida exitosamente")
            response
        } catch (e: Exception) {
            println("DEBUG API ERROR: ${e.message}")
            e.printStackTrace()
            MmaResponse(response = emptyList(), errors = null)
        }
    }
}
