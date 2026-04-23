package com.ucb.app.config.data.datasource

expect class RemoteConfigDataSource {
    suspend fun fetchValue(key: String): String
}