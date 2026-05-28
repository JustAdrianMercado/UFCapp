package com.ucb.app.onboarding.presentation.state

sealed interface OnboardingEvent {
    data object OnNextClick : OnboardingEvent
    data object OnPreviousClick : OnboardingEvent
    data object OnSkipClick : OnboardingEvent
    data object OnStartClick : OnboardingEvent
}