package com.ucb.app.live.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.live.domain.usecase.GetLiveEventsUseCase
import com.ucb.app.live.presentation.state.LiveUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LiveViewModel(
    private val getLiveEventsUseCase: GetLiveEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveUiState())
    val uiState: StateFlow<LiveUiState> = _uiState

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                _uiState.value = LiveUiState(
                    events = getLiveEventsUseCase()
                )
            } catch (e: Exception) {
                _uiState.value = LiveUiState(
                    error = e.message
                )
            }
        }
    }
}