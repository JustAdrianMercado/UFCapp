package com.ucb.app.profile.presentation.state

sealed class ProfileEditEvent {
    data object LoadProfile : ProfileEditEvent()
    data object OnBackClick : ProfileEditEvent()
    data object OnSaveClick : ProfileEditEvent()
    data object OnChangePasswordClick : ProfileEditEvent()
    data class OnFirstNameChanged(val value: String) : ProfileEditEvent()
    data class OnLastNameChanged(val value: String) : ProfileEditEvent()
    data class OnUsernameChanged(val value: String) : ProfileEditEvent()
    data class OnEmailChanged(val value: String) : ProfileEditEvent()
    data class OnPhoneNumberChanged(val value: String) : ProfileEditEvent()
    data class OnBirthdayChanged(val value: String) : ProfileEditEvent()
    data class OnGenderChanged(val value: String) : ProfileEditEvent()
}
