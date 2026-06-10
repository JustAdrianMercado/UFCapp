package com.ucb.app.live.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.live.domain.usecase.GetLiveEventsUseCase
import com.ucb.app.live.presentation.state.LiveScreenEffect
import com.ucb.app.live.presentation.state.LiveScreenEvent
import com.ucb.app.live.presentation.state.LiveUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LiveViewModel(
    private val getLiveEventsUseCase: GetLiveEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveUiState())
    val uiState: StateFlow<LiveUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LiveScreenEffect>()
    val effect: SharedFlow<LiveScreenEffect> = _effect.asSharedFlow()

    init {
        onEvent(LiveScreenEvent.LoadLiveEvents)
    }

    fun onEvent(event: LiveScreenEvent) {
        when (event) {
            is LiveScreenEvent.LoadLiveEvents -> loadEvents()
            is LiveScreenEvent.OnEventClick -> {
                viewModelScope.launch {
                    _effect.emit(LiveScreenEffect.NavigateToEventDetail(event.eventId))
                }
            }
            is LiveScreenEvent.OnParamountClick -> {
                viewModelScope.launch {
                    _effect.emit(LiveScreenEffect.OpenParamountExternal)
                }
            }
        }
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val events = getLiveEventsUseCase()
                _uiState.value = LiveUiState(
                    isLoading = false,
                    events = events
                )
            } catch (e: Exception) {
                _uiState.value = LiveUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
