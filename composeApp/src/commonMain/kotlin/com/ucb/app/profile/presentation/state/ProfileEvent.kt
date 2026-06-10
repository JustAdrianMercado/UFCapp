package com.ucb.app.profile.presentation.state

sealed class ProfileEvent {
    data object LoadProfile : ProfileEvent()
    data object OnEditProfileClick : ProfileEvent()
    data object OnLogOutClick : ProfileEvent()
}
