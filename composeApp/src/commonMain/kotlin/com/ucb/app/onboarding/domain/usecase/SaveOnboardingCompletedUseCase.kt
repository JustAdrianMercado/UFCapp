package com.ucb.app.onboarding.domain.usecase

import com.ucb.app.onboarding.domain.repository.OnboardingRepository

class SaveOnboardingCompletedUseCase(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke() = repository.saveCompleted()
}