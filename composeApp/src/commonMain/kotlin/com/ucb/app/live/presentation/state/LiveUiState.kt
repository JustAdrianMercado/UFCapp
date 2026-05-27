package com.ucb.app.live.presentation.state

import com.ucb.app.live.domain.model.LiveEvent

data class LiveUiState(
    val isLoading: Boolean = false,
    val events: List<LiveEvent> = emptyList(),
    val error: String? = null
)