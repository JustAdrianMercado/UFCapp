package com.ucb.app.portafolio.presentation.state

sealed interface PortafolioEvent {
    data class OnPathChanged(val path: String) : PortafolioEvent
    data class OnValueChanged(val value: String) : PortafolioEvent
    data object OnSaveClicked : PortafolioEvent
    data object OnGetClicked : PortafolioEvent
}