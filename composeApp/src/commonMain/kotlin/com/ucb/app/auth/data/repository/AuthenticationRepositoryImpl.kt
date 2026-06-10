package com.ucb.app.auth.data.repository

import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.model.RegisterModel
import com.ucb.app.auth.domain.repository.AuthenticationRepository
import com.ucb.app.firebase.FirebaseManager
import com.ucb.app.profile.domain.model.ProfileModel
import com.ucb.app.session.SessionManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthenticationRepositoryImpl(
    private val firebaseManager: FirebaseManager,
    private val sessionManager: SessionManager
) : AuthenticationRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun login(model: LoginModel): Boolean {
        if (model.email.isBlank() || model.password.isBlank()) return false

        val userId = model.email.toUserId()
        val storedUser = firebaseManager.getData("auth/$userId") ?: return false
        val authUser = runCatching { json.decodeFromString<AuthUserDto>(storedUser) }.getOrNull() ?: return false
        val success = authUser.password == model.password
        if (success) {
            sessionManager.startSession(userId)
        }
        return success
    }

    override suspend fun register(model: RegisterModel): Boolean {
        if (model.firstName.isBlank() || model.lastName.isBlank() || model.email.isBlank() || model.password.isBlank()) return false

        val userId = model.email.toUserId()
        val fullName = "${model.firstName.trim()} ${model.lastName.trim()}".trim()
        val authUser = AuthUserDto(
            id = userId,
            firstName = model.firstName.trim(),
            lastName = model.lastName.trim(),
            username = model.username.trim(),
            fullName = fullName,
            email = model.email.trim().lowercase(),
            phoneNumber = model.phoneNumber.trim(),
            birthday = model.birthday.trim(),
            gender = model.gender.trim(),
            password = model.password
        )
        val profile = ProfileModel(
            id = userId,
            firstName = model.firstName.trim(),
            lastName = model.lastName.trim(),
            username = model.username.trim(),
            name = fullName,
            email = model.email.trim().lowercase(),
            cellphone = model.phoneNumber.trim(),
            description = model.username.trim(),
            birthday = model.birthday.trim(),
            gender = model.gender.trim(),
            pathUrl = "https://via.placeholder.com/300"
        )

        firebaseManager.saveData("auth/$userId", json.encodeToString(authUser))
        firebaseManager.saveData("profiles/$userId", json.encodeToString(profile))
        sessionManager.startSession(userId)
        return true
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        if (newPassword.isBlank()) return false
        val userId = sessionManager.currentUserId ?: return false
        val storedUser = firebaseManager.getData("auth/$userId") ?: return false
        val authUser = runCatching { json.decodeFromString<AuthUserDto>(storedUser) }.getOrNull() ?: return false
        firebaseManager.saveData("auth/$userId", json.encodeToString(authUser.copy(password = newPassword)))
        return true
    }

    private fun String.toUserId(): String = trim()
        .lowercase()
        .replace(".", "_")
        .replace("#", "_")
        .replace("$", "_")
        .replace("[", "_")
        .replace("]", "_")
        .replace("/", "_")
}

@Serializable
private data class AuthUserDto(
    val id: String,
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthday: String = "",
    val gender: String = "",
    val password: String
)
