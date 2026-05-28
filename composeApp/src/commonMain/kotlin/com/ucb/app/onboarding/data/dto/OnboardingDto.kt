package com.ucb.app.onboarding.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingConfigDto(
    @SerialName("onboarding_config")
    val onboardingConfig: List<OnboardingDto>
)

@Serializable
data class OnboardingDto(
    val id: Int,
    val title: Map<String, String>,
    val description: Map<String, String>,

    @SerialName("image_url")
    val imageUrl: Map<String, String>
)