package com.ucb.app.portafolio.presentation.state

data class PortafolioUiState(
    val path: String = "prueba/mensaje",
    val value: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)