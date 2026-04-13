package com.ucb.app.portafolio.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.portafolio.domain.usecase.SavePortafolioDataUseCase
import com.ucb.app.portafolio.presentation.state.PortafolioEffect
import com.ucb.app.portafolio.presentation.state.PortafolioEvent
import com.ucb.app.portafolio.presentation.state.PortafolioUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PortafolioViewModel(
    private val savePortafolioDataUseCase: SavePortafolioDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PortafolioUiState())
    val uiState: StateFlow<PortafolioUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<PortafolioEffect>()
    val effect: SharedFlow<PortafolioEffect> = _effect.asSharedFlow()

    fun onEvent(event: PortafolioEvent) {
        when (event) {
            is PortafolioEvent.OnPathChanged -> {
                _uiState.value = _uiState.value.copy(path = event.path)
            }

            is PortafolioEvent.OnValueChanged -> {
                _uiState.value = _uiState.value.copy(value = event.value)
            }

            PortafolioEvent.OnSaveClicked -> {
                saveData()
            }
        }
    }

    private fun saveData() {
        val currentState = _uiState.value

        if (currentState.path.isBlank() || currentState.value.isBlank()) {
            viewModelScope.launch {
                _effect.emit(PortafolioEffect.ShowMessage("Completa el path y el valor"))
            }
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(
                isLoading = true,
                successMessage = null,
                errorMessage = null
            )

            try {
                savePortafolioDataUseCase(
                    currentState.path,
                    currentState.value
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Dato guardado correctamente"
                )

                _effect.emit(PortafolioEffect.ShowMessage("Dato guardado en Firebase"))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido"
                )

                _effect.emit(PortafolioEffect.ShowMessage("Error al guardar"))
            }
        }
    }
}