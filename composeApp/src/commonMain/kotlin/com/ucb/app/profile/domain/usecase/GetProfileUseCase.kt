package com.ucb.app.profile.domain.usecase

import com.ucb.app.profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(id: String) = repository.findById(id)
}