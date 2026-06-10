package com.ucb.app.onboarding.data.datasource

import com.ucb.app.onboarding.data.dto.OnboardingDto

actual class OnboardingRemoteDataSource {
    actual suspend fun getConfig(): List<OnboardingDto> {
        return emptyList()
    }
}
