package com.ucb.app.onboarding.data.datasource

expect class OnboardingPreferencesDataSource {
    fun isCompleted(): Boolean
    fun saveCompleted()
}