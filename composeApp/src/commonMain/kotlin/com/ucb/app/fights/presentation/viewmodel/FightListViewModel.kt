package com.ucb.app.fights.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.fights.domain.usecase.GetUpcomingFightsUseCase
import com.ucb.app.fights.presentation.state.FightListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FightListViewModel(
    private val getUpcomingFightsUseCase: GetUpcomingFightsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FightListUiState())
    val uiState: StateFlow<FightListUiState> = _uiState

    init {
        loadFights()
    }

    private fun loadFights() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val fights = getUpcomingFightsUseCase()
                _uiState.value = FightListUiState(fights = fights)
            } catch (e: Exception) {
                _uiState.value = FightListUiState(error = e.message)
            }
        }
    }
}