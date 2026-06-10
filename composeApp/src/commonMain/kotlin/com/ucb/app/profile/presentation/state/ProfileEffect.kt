package com.ucb.app.profile.presentation.state

sealed class ProfileEffect {
    data object NavigateToEditProfile : ProfileEffect()
    data object NavigateToLogin : ProfileEffect()
}
