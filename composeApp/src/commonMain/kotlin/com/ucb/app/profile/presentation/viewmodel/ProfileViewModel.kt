package com.ucb.app.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.profile.domain.usecase.GetProfileUseCase
import com.ucb.app.profile.presentation.state.ProfileEffect
import com.ucb.app.profile.presentation.state.ProfileEvent
import com.ucb.app.profile.presentation.state.ProfileUiState
import com.ucb.app.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect: SharedFlow<ProfileEffect> = _effect.asSharedFlow()

    init {
        onEvent(ProfileEvent.LoadProfile)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadProfile -> loadProfile()
            is ProfileEvent.OnEditProfileClick -> {
                viewModelScope.launch {
                    _effect.emit(ProfileEffect.NavigateToEditProfile)
                }
            }
            is ProfileEvent.OnLogOutClick -> {
                sessionManager.clearSession()
                viewModelScope.launch {
                    _effect.emit(ProfileEffect.NavigateToLogin)
                }
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    profile = getProfileUseCase("1")
                )
            } catch (e: Exception) {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
