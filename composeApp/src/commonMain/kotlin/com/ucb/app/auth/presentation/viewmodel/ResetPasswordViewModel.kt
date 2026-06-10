package com.ucb.app.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.auth.domain.usecase.UpdatePasswordUseCase
import com.ucb.app.auth.presentation.state.ResetPasswordEffect
import com.ucb.app.auth.presentation.state.ResetPasswordEvent
import com.ucb.app.auth.presentation.state.ResetPasswordUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ResetPasswordEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.value, error = null)
            }
            is ResetPasswordEvent.OnConfirmPasswordChanged -> {
                _state.value = _state.value.copy(confirmPassword = event.value, error = null)
            }
            ResetPasswordEvent.OnUpdateClick -> updatePassword()
        }
    }

    private fun updatePassword() {
        val currentState = _state.value
        val validationMessage = when {
            currentState.password.length < 6 -> "Password must be at least 6 characters"
            currentState.password != currentState.confirmPassword -> "Passwords do not match"
            else -> null
        }
        if (validationMessage != null) {
            _state.value = currentState.copy(error = validationMessage)
            emitMessage(validationMessage)
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                val success = updatePasswordUseCase(currentState.password)
                if (success) {
                    _state.value = _state.value.copy(isLoading = false)
                    _effect.emit(ResetPasswordEffect.ShowMessage("Password updated"))
                    _effect.emit(ResetPasswordEffect.NavigateBack)
                } else {
                    val message = "Could not update password"
                    _state.value = _state.value.copy(isLoading = false, error = message)
                    _effect.emit(ResetPasswordEffect.ShowMessage(message))
                }
            } catch (e: Exception) {
                val message = e.message ?: "Could not update password"
                _state.value = _state.value.copy(isLoading = false, error = message)
                _effect.emit(ResetPasswordEffect.ShowMessage(message))
            }
        }
    }

    private fun emitMessage(message: String) {
        viewModelScope.launch {
            _effect.emit(ResetPasswordEffect.ShowMessage(message))
        }
    }
}
