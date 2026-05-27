package com.ucb.app.profile.presentation.state

import com.ucb.app.profile.domain.model.ProfileModel

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: ProfileModel? = null,
    val error: String? = null
)