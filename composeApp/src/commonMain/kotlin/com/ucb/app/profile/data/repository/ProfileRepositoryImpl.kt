package com.ucb.app.profile.data.repository

import com.ucb.app.portafolio.data.datasource.FirebaseManager
import com.ucb.app.profile.domain.model.ProfileModel
import com.ucb.app.profile.domain.repository.ProfileRepository
import com.ucb.app.session.SessionManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProfileRepositoryImpl(
    private val firebaseManager: FirebaseManager,
    private val sessionManager: SessionManager
) : ProfileRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private var fakeProfile = ProfileModel(
        id = "1",
        firstName = "Sabrina",
        lastName = "Aryan",
        username = "@Sabrina",
        name = "Sabrina Aryan",
        email = "SabrinaAry208@gmail.com",
        cellphone = "+234 904 6470",
        description = "@Sabrina",
        pathUrl = "https://via.placeholder.com/300",
        birthday = "Birth",
        gender = "Gender"
    )

    override suspend fun update(profile: ProfileModel) {
        val currentUserId = sessionManager.currentUserId ?: profile.id
        val newUserId = profile.email.toUserId()
        val updatedProfile = profile.copy(id = newUserId, email = profile.email.trim().lowercase())
        fakeProfile = updatedProfile
        firebaseManager.saveData("profiles/$newUserId", json.encodeToString(updatedProfile))
        syncAuthProfile(currentUserId, updatedProfile)
        sessionManager.updateSessionUserId(newUserId)
    }

    override suspend fun create(profile: ProfileModel) {
        val userId = profile.email.toUserId()
        val profileToSave = profile.copy(id = userId, email = profile.email.trim().lowercase())
        fakeProfile = profileToSave
        firebaseManager.saveData("profiles/$userId", json.encodeToString(profileToSave))
    }

    override suspend fun findById(id: String): ProfileModel {
        val userId = sessionManager.currentUserId ?: id
        val storedProfile = firebaseManager.getData("profiles/$userId")
        return storedProfile
            ?.let { runCatching { json.decodeFromString<ProfileModel>(it) }.getOrNull() }
            ?: fakeProfile.copy(id = userId)
    }

    private suspend fun syncAuthProfile(currentUserId: String, profile: ProfileModel) {
        val storedAuth = firebaseManager.getData("auth/$currentUserId") ?: return
        val authUser = runCatching { json.decodeFromString<AuthUserDto>(storedAuth) }.getOrNull() ?: return
        val updatedAuth = authUser.copy(
            id = profile.id,
            firstName = profile.firstName,
            lastName = profile.lastName,
            username = profile.username,
            fullName = profile.name,
            email = profile.email,
            phoneNumber = profile.cellphone,
            birthday = profile.birthday,
            gender = profile.gender
        )
        firebaseManager.saveData("auth/${profile.id}", json.encodeToString(updatedAuth))
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
