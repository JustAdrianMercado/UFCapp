package com.ucb.app.crypto.data.service

import com.ucb.app.crypto.data.datasource.CryptoRemoteDataSource
import com.ucb.app.crypto.data.dto.CryptoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CryptoService: CryptoRemoteDataSource {
    private val client = HttpClient{
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getCryptos(): List<CryptoDto> {
        val response = client.get("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd")
        return try {
            response.body<List<CryptoDto>>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}