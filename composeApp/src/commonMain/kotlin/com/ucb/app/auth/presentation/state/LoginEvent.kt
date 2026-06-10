package com.ucb.app.auth.presentation.state

sealed interface LoginEvent {
    data class OnFirstNameChanged(val value: String) : LoginEvent
    data class OnLastNameChanged(val value: String) : LoginEvent
    data class OnUsernameChanged(val value: String) : LoginEvent
    data class OnEmailChanged(val value: String) : LoginEvent
    data class OnPhoneNumberChanged(val value: String) : LoginEvent
    data class OnBirthdayChanged(val value: String) : LoginEvent
    data class OnGenderChanged(val value: String) : LoginEvent
    data class OnPasswordChanged(val value: String) : LoginEvent
    data object OnToggleModeClick : LoginEvent
    data object OnClick : LoginEvent
}
