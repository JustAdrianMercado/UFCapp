package com.ucb.app.auth.domain.usecase

import com.ucb.app.auth.domain.repository.AuthenticationRepository

class UpdatePasswordUseCase(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(newPassword: String): Boolean {
        return repository.updatePassword(newPassword)
    }
}
