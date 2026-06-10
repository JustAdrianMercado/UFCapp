package com.ucb.app.auth.presentation.state

sealed interface ResetPasswordEvent {
    data class OnPasswordChanged(val value: String) : ResetPasswordEvent
    data class OnConfirmPasswordChanged(val value: String) : ResetPasswordEvent
    data object OnUpdateClick : ResetPasswordEvent
}
