package com.ucb.app.onboarding.data.datasource

import com.ucb.app.onboarding.data.dto.OnboardingDto

expect class OnboardingRemoteDataSource {
    suspend fun getConfig(): List<OnboardingDto>
}