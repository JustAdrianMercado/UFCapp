package com.ucb.app.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.auth.domain.model.LoginModel
import com.ucb.app.auth.domain.model.RegisterModel
import com.ucb.app.auth.domain.usecase.DoLoginUseCase
import com.ucb.app.auth.domain.usecase.DoRegisterUseCase
import com.ucb.app.auth.presentation.state.LoginEffect
import com.ucb.app.auth.presentation.state.LoginEvent
import com.ucb.app.auth.presentation.state.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: DoLoginUseCase,
    private val registerUseCase: DoRegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnFirstNameChanged -> {
                _state.value = _state.value.copy(firstName = event.value, error = null)
            }

            is LoginEvent.OnLastNameChanged -> {
                _state.value = _state.value.copy(lastName = event.value, error = null)
            }

            is LoginEvent.OnUsernameChanged -> {
                _state.value = _state.value.copy(username = event.value, error = null)
            }

            is LoginEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(email = event.value, error = null)
            }

            is LoginEvent.OnPhoneNumberChanged -> {
                _state.value = _state.value.copy(phoneNumber = event.value, error = null)
            }

            is LoginEvent.OnBirthdayChanged -> {
                _state.value = _state.value.copy(birthday = event.value, error = null)
            }

            is LoginEvent.OnGenderChanged -> {
                _state.value = _state.value.copy(gender = event.value, error = null)
            }

            is LoginEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.value, error = null)
            }

            LoginEvent.OnToggleModeClick -> {
                _state.value = _state.value.copy(
                    isRegisterMode = !_state.value.isRegisterMode,
                    error = null
                )
            }

            LoginEvent.OnClick -> {
                if (_state.value.isRegisterMode) {
                    sendRegister()
                } else {
                    sendLogin()
                }
            }
        }
    }

    private fun sendLogin() {
        viewModelScope.launch {
            if (!_state.value.email.isValidEmail()) {
                val message = "Enter a valid email address"
                _state.value = _state.value.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val success = loginUseCase(
                    LoginModel(
                        email = _state.value.email,
                        password = _state.value.password
                    )
                )

                if (success) {
                    _state.value = _state.value.copy(isLoading = false)
                    _effect.emit(LoginEffect.NavigateToHome)
                } else {
                    val message = "Invalid email or password"
                    _state.value = _state.value.copy(isLoading = false, error = message)
                    _effect.emit(LoginEffect.ShowError(message))
                }
            } catch (e: Exception) {
                val message = e.message ?: "Could not connect to Firebase"
                _state.value = _state.value.copy(isLoading = false, error = message)
                _effect.emit(LoginEffect.ShowError(message))
            }
        }
    }

    private fun sendRegister() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState.firstName.isBlank() || currentState.lastName.isBlank()) {
                val message = "Enter your first and last names"
                _state.value = currentState.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }
            if (currentState.username.isBlank() || currentState.phoneNumber.isBlank()) {
                val message = "Enter username and phone number"
                _state.value = currentState.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }
            if (currentState.birthday.isBlank() || currentState.gender.isBlank()) {
                val message = "Enter birthday and gender"
                _state.value = currentState.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }
            if (!currentState.email.isValidEmail()) {
                val message = "Enter a valid email address"
                _state.value = currentState.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }
            if (currentState.password.length < 6) {
                val message = "Password must be at least 6 characters"
                _state.value = currentState.copy(error = message)
                _effect.emit(LoginEffect.ShowError(message))
                return@launch
            }

            _state.value = currentState.copy(isLoading = true, error = null)

            try {
                val success = registerUseCase(
                    RegisterModel(
                        firstName = currentState.firstName,
                        lastName = currentState.lastName,
                        username = currentState.username,
                        email = currentState.email,
                        phoneNumber = currentState.phoneNumber,
                        birthday = currentState.birthday,
                        gender = currentState.gender,
                        password = currentState.password
                    )
                )

                if (success) {
                    _state.value = _state.value.copy(isLoading = false)
                    _effect.emit(LoginEffect.NavigateToHome)
                } else {
                    val message = "Complete all register fields"
                    _state.value = _state.value.copy(isLoading = false, error = message)
                    _effect.emit(LoginEffect.ShowError(message))
                }
            } catch (e: Exception) {
                val message = e.message ?: "Could not save user in Firebase"
                _state.value = _state.value.copy(isLoading = false, error = message)
                _effect.emit(LoginEffect.ShowError(message))
            }
        }
    }

    private fun String.isValidEmail(): Boolean {
        return trim().matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }
}
