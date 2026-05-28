package com.ucb.app.onboarding.presentation.state

import com.ucb.app.onboarding.domain.model.OnboardingPage

data class OnboardingUiState(
    val isLoading: Boolean = false,
    val pages: List<OnboardingPage> = emptyList(),
    val currentIndex: Int = 0,
    val error: String? = null
) {
    val currentPage: OnboardingPage?
        get() = pages.getOrNull(currentIndex)

    val isFirstPage: Boolean
        get() = currentIndex == 0

    val isLastPage: Boolean
        get() = pages.isNotEmpty() && currentIndex == pages.lastIndex
}