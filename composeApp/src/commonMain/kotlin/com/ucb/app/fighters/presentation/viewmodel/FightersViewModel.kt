package com.ucb.app.fighters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.fighters.domain.usecase.GetFightersUseCase
import com.ucb.app.fighters.presentation.state.FightersEffect
import com.ucb.app.fighters.presentation.state.FightersEvent
import com.ucb.app.fighters.presentation.state.FightersUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FightersViewModel(
    private val getFightersUseCase: GetFightersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FightersUiState())
    val uiState: StateFlow<FightersUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<FightersEffect>()
    val effect: SharedFlow<FightersEffect> = _effect.asSharedFlow()

    init {
        onEvent(FightersEvent.LoadFighters)
    }

    fun onEvent(event: FightersEvent) {
        when (event) {
            is FightersEvent.LoadFighters -> loadFighters()
            is FightersEvent.OnFighterClick -> {
                viewModelScope.launch {
                    _effect.emit(FightersEffect.NavigateToFighterDetail(event.fighterId))
                }
            }
        }
    }

    private fun loadFighters() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val fighters = getFightersUseCase()
                _uiState.value = FightersUiState(
                    isLoading = false,
                    fighters = fighters
                )
            } catch (e: Exception) {
                _uiState.value = FightersUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
