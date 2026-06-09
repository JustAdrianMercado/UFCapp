package com.ucb.app.fighters.data.repository

import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.domain.repository.FighterRepository

class FighterRepositoryImpl : FighterRepository {
    override suspend fun getFighters(): List<Fighter> {
        return listOf(
            Fighter("1", "Islam Makhachev", "The Eagle’s Heir", "Lightweight", "27-1-0", "https://via.placeholder.com/300"),
            Fighter("2", "Alex Pereira", "Poatan", "Light Heavyweight", "10-2-0", "https://via.placeholder.com/300"),
            Fighter("3", "Ilia Topuria", "El Matador", "Featherweight", "16-0-0", "https://via.placeholder.com/300"),
            Fighter("4", "Sean O'Malley", "Suga", "Bantamweight", "18-2-0", "https://via.placeholder.com/300"),
        )
    }
}