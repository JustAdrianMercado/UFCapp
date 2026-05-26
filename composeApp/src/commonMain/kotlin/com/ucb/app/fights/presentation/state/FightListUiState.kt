package com.ucb.app.fights.presentation.state

import com.ucb.app.fights.domain.model.Fight

data class FightListUiState(
    val isLoading: Boolean = false,
    val fights: List<Fight> = emptyList(),
    val error: String? = null
)