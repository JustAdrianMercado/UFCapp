package com.ucb.app.auth.presentation.state

data class LoginUiState(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthday: String = "",
    val gender: String = "",
    val password: String = "",
    val isRegisterMode: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
