package com.ucb.app.profile.presentation.state

data class ProfileEditUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val profileId: String = "1",
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthday: String = "",
    val gender: String = "",
    val avatarUrl: String = "",
    val error: String? = null
) {
    val canSave: Boolean
        get() = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
}
