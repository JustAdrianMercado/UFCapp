package com.ucb.app.fighters.domain.repository

import com.ucb.app.fighters.domain.model.Fighter

interface FighterRepository {
    suspend fun getFighters(): List<Fighter>
}