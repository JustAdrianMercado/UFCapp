package com.ucb.app.auth.domain.repository

import com.ucb.app.auth.domain.model.LoginModel

interface AuthenticationRepository {
    suspend fun login(model: LoginModel): Boolean
}