package com.ucb.app.onboarding.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ucb.app.onboarding.data.dto.OnboardingDto
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

actual class OnboardingRemoteDataSource(
    private val remoteConfig: FirebaseRemoteConfig
) {
    actual suspend fun getConfig(): List<OnboardingDto> {
        remoteConfig.fetchAndActivate().await()

        val json = remoteConfig.getString("onboarding_config")

        return Json.decodeFromString(json)
    }
}