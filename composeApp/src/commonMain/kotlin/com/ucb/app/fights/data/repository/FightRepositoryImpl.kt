package com.ucb.app.fights.data.repository

import com.ucb.app.fights.domain.model.Fight
import com.ucb.app.fights.domain.repository.FightRepository

class FightRepositoryImpl : FightRepository {

    override suspend fun getUpcomingFights(): List<Fight> {
        return listOf(
            Fight(
                id = "1",
                fighter1 = "Adesanya",
                fighter2 = "Pereira",
                eventName = "UFC 300",
                date = "March 28",
                imageUrl = "https://via.placeholder.com/300"
            ),
            Fight(
                id = "2",
                fighter1 = "Oliveira",
                fighter2 = "Makhachev",
                eventName = "UFC 301",
                date = "April 10",
                imageUrl = "https://via.placeholder.com/300"
            )
        )
    }
}