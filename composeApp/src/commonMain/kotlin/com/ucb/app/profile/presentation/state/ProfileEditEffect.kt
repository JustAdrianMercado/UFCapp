package com.ucb.app.profile.presentation.state

sealed class ProfileEditEffect {
    data object NavigateBack : ProfileEditEffect()
    data object NavigateToChangePassword : ProfileEditEffect()
    data class ShowMessage(val message: String) : ProfileEditEffect()
}
