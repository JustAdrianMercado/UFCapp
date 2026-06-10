package com.ucb.app.fighters.presentation.state

import com.ucb.app.fighters.domain.model.Fighter

data class FightersUiState(
    val isLoading: Boolean = false,
    val fighters: List<Fighter> = emptyList(),
    val searchQuery: String = "",
    val filteredFighters: List<Fighter> = emptyList(),
    val error: String? = null
)