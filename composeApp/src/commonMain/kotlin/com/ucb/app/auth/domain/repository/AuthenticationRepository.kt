package com.ucb.app.auth.domain.repository

import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.model.RegisterModel

interface AuthenticationRepository {
    suspend fun login(model: LoginModel): Boolean
    suspend fun register(model: RegisterModel): Boolean
    suspend fun updatePassword(newPassword: String): Boolean
}
