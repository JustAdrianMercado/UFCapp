package com.ucb.app.portafolio.presentation.state

sealed interface PortafolioEffect {
    data class ShowMessage(val message: String) : PortafolioEffect
}