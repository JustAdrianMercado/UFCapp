package com.ucb.app.onboarding.domain.repository

import com.ucb.app.onboarding.domain.model.OnboardingPage

interface OnboardingRepository {

    suspend fun getPages(): List<OnboardingPage>

    suspend fun saveCompleted()

    suspend fun isCompleted(): Boolean
}