package com.ucb.app.onboarding.data.datasource

import android.content.Context

actual class OnboardingPreferencesDataSource(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences(
        "onboarding_prefs",
        Context.MODE_PRIVATE
    )

    actual fun isCompleted(): Boolean {
        return prefs.getBoolean("onboarding_completed", false)
    }

    actual fun saveCompleted() {
        prefs.edit()
            .putBoolean("onboarding_completed", true)
            .apply()
    }
}