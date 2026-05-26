package com.ucb.app.fights.domain.repository

import com.ucb.app.fights.domain.model.Fight

interface FightRepository {
    suspend fun getUpcomingFights(): List<Fight>
}