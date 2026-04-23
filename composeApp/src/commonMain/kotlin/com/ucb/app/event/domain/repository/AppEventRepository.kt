package com.ucb.app.event.domain.repository

interface AppEventRepository {
    suspend fun registerEvent(type: String)
}