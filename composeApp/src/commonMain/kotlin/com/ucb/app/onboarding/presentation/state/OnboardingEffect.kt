package com.ucb.app.onboarding.presentation.state

sealed interface OnboardingEffect {
    data object NavigateToHome : OnboardingEffect
}