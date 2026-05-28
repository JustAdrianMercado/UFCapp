package com.ucb.app.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.onboarding.domain.usecase.GetOnboardingPagesUseCase
import com.ucb.app.onboarding.domain.usecase.SaveOnboardingCompletedUseCase
import com.ucb.app.onboarding.presentation.state.OnboardingEffect
import com.ucb.app.onboarding.presentation.state.OnboardingEvent
import com.ucb.app.onboarding.presentation.state.OnboardingUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val getOnboardingPagesUseCase: GetOnboardingPagesUseCase,
    private val saveOnboardingCompletedUseCase: SaveOnboardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<OnboardingEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadPages()
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnNextClick -> nextPage()
            OnboardingEvent.OnPreviousClick -> previousPage()
            OnboardingEvent.OnSkipClick -> navigateHomeWithoutSaving()
            OnboardingEvent.OnStartClick -> completeOnboarding()
        }
    }

    private fun loadPages() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val pages = getOnboardingPagesUseCase()

                _state.value = OnboardingUiState(
                    isLoading = false,
                    pages = pages,
                    currentIndex = 0
                )
            } catch (e: Exception) {
                _state.value = OnboardingUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun nextPage() {
        val current = _state.value

        if (!current.isLastPage) {
            _state.value = current.copy(
                currentIndex = current.currentIndex + 1
            )
        }
    }

    private fun previousPage() {
        val current = _state.value

        if (!current.isFirstPage) {
            _state.value = current.copy(
                currentIndex = current.currentIndex - 1
            )
        }
    }

    private fun navigateHomeWithoutSaving() {
        viewModelScope.launch {
            _effect.emit(OnboardingEffect.NavigateToHome)
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            saveOnboardingCompletedUseCase()
            _effect.emit(OnboardingEffect.NavigateToHome)
        }
    }
}