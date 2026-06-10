package com.ucb.app.auth.domain.model

data class RegisterModel(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val birthday: String,
    val gender: String,
    val password: String
)
