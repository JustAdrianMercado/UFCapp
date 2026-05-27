package com.ucb.app.live.domain.repository

import com.ucb.app.live.domain.model.LiveEvent

interface LiveRepository {
    suspend fun getLiveEvents(): List<LiveEvent>
}