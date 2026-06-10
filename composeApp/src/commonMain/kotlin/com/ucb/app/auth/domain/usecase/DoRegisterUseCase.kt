package com.ucb.app.auth.domain.usecase

import com.ucb.app.auth.domain.model.RegisterModel
import com.ucb.app.auth.domain.repository.AuthenticationRepository

class DoRegisterUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(model: RegisterModel): Boolean {
        return repository.register(model)
    }
}
