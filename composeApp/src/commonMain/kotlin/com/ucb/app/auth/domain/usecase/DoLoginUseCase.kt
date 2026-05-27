package com.ucb.app.auth.domain.usecase

import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.repository.AuthenticationRepository

class DoLoginUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(model: LoginModel): Boolean {
        return repository.login(model)
    }
}