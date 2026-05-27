package com.ucb.app.fighters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.fighters.domain.usecase.GetFightersUseCase
import com.ucb.app.fighters.presentation.state.FightersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FightersViewModel(
    private val getFightersUseCase: GetFightersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FightersUiState())
    val uiState: StateFlow<FightersUiState> = _uiState

    init {
        loadFighters()
    }

    private fun loadFighters() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                _uiState.value = FightersUiState(
                    fighters = getFightersUseCase()
                )
            } catch (e: Exception) {
                _uiState.value = FightersUiState(
                    error = e.message
                )
            }
        }
    }
}