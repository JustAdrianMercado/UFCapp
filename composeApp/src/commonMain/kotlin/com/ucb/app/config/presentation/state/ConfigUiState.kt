package com.ucb.app.config.presentation.state

data class ConfigUiState(
    val isLoading: Boolean = false,
    val value: String = "",
    val error: String? = null
)