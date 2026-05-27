package com.ucb.app.auth.data.repository

import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override suspend fun login(model: LoginModel): Boolean {
        return model.email.isNotBlank() && model.password.isNotBlank()
    }
}