package com.ucb.app.config.data.datasource

actual class RemoteConfigDataSource {
    actual suspend fun fetchValue(key: String): String {
        return ""
    }
}
