package com.ucb.app.auth.presentation.state

sealed interface LoginEvent {
    data class OnEmailChanged(val value: String) : LoginEvent
    data class OnPasswordChanged(val value: String) : LoginEvent
    data object OnClick : LoginEvent
}