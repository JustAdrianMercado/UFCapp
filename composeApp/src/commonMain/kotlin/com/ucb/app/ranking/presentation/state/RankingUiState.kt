package com.ucb.app.ranking.presentation.state

import com.ucb.app.ranking.domain.model.FighterRanking

data class RankingUiState(
    val isLoading: Boolean = false,
    val rankings: List<FighterRanking> = emptyList(),
    val error: String? = null
)