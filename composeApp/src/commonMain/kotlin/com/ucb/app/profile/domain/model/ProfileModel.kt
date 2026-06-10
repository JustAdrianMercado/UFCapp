package com.ucb.app.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val id: String,
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val name: String,
    val email: String,
    val cellphone: String,
    val description: String,
    val pathUrl: String,
    val birthday: String = "",
    val gender: String = ""
)
