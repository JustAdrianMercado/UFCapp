package com.ucb.app.fighters.data.service

import com.ucb.app.fighters.data.dto.FighterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FighterApiService {
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

    suspend fun getFighters(search: String? = null): FighterResponse {
        return try {
            client.get("$baseUrl/fighters") {
                header("x-apisports-key", apiKey)
                search?.let {
                    parameter("search", it)
                }
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            FighterResponse(response = emptyList(), errors = null)
        }
    }
}
