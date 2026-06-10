package com.ucb.app.auth.presentation.state

sealed interface ResetPasswordEffect {
    data object NavigateBack : ResetPasswordEffect
    data class ShowMessage(val message: String) : ResetPasswordEffect
}
