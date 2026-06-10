package com.ucb.app.onboarding.data.datasource

actual class OnboardingPreferencesDataSource {
    actual fun isCompleted(): Boolean {
        return false
    }

    actual fun saveCompleted() {
    }
}
