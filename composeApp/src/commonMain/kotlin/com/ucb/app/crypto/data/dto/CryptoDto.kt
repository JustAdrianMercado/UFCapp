package com.ucb.app.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDto(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("market_cap_rank")
    val marketCapRank: Int,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("high_24h")
    val high24h: Double,
    @SerialName("low_24h")
    val low24h: Double
)