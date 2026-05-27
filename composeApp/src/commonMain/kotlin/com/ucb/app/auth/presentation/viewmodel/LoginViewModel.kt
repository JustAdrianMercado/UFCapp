package com.ucb.app.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.usecase.DoLoginUseCase
import com.ucb.app.auth.presentation.state.LoginEffect
import com.ucb.app.auth.presentation.state.LoginEvent
import com.ucb.app.auth.presentation.state.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: DoLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(email = event.value)
            }

            is LoginEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.value)
            }

            LoginEvent.OnClick -> {
                sendLogin()
            }
        }
    }

    private fun sendLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val success = loginUseCase(
                LoginModel(
                    email = _state.value.email,
                    password = _state.value.password
                )
            )

            _state.value = _state.value.copy(isLoading = false)

            if (success) {
                _effect.emit(LoginEffect.NavigateToHome)
            } else {
                _effect.emit(LoginEffect.ShowError("Completa email y contraseña"))
            }
        }
    }
}