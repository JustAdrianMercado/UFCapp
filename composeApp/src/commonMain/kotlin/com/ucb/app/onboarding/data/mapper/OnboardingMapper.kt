package com.ucb.app.onboarding.data.mapper

import com.ucb.app.onboarding.data.dto.OnboardingDto
import com.ucb.app.onboarding.domain.model.OnboardingPage

fun OnboardingDto.toDomain(language: String): OnboardingPage {
    return OnboardingPage(
        id = id,
        title = title[language] ?: title["en"].orEmpty(),
        description = description[language] ?: description["en"].orEmpty(),
        imageUrl = imageUrl[language] ?: imageUrl["en"].orEmpty()
    )
}