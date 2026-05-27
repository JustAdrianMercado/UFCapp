package com.ucb.app.live.data.repository

import com.ucb.app.live.domain.model.LiveEvent
import com.ucb.app.live.domain.repository.LiveRepository

class LiveRepositoryImpl : LiveRepository {
    override suspend fun getLiveEvents(): List<LiveEvent> {
        return listOf(
            LiveEvent("1", "Paramount+", "Preventa - UFC Fight Club", "Wed, Jan 7", "12:00 PM GMT-4", "Disponible"),
            LiveEvent("2", "Paramount+", "Preventa - UFC Newsletter", "Thu, Jan 8", "12:00 PM GMT-4", "Disponible"),
            LiveEvent("3", "Paramount+", "A la venta - Público", "Fri, Jan 9", "12:00 PM GMT-4", "Disponible")
        )
    }
}