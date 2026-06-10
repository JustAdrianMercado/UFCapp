package com.ucb.app.fights.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.fights.domain.model.Fight
import com.ucb.app.fights.domain.usecase.GetUpcomingFightsUseCase
import com.ucb.app.fights.presentation.state.FightListEffect
import com.ucb.app.fights.presentation.state.FightListEvent
import com.ucb.app.fights.presentation.state.FightListUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FightListViewModel(
    private val getUpcomingFightsUseCase: GetUpcomingFightsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FightListUiState())
    val uiState: StateFlow<FightListUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<FightListEffect>()
    val effect: SharedFlow<FightListEffect> = _effect.asSharedFlow()

    init {
        onEvent(FightListEvent.LoadFights)
    }

    fun onEvent(event: FightListEvent) {
        when (event) {
            is FightListEvent.LoadFights -> loadFights()
            is FightListEvent.OnFightClick -> {
                viewModelScope.launch {
                    _effect.emit(FightListEffect.NavigateToFightDetail(event.fightId))
                }
            }
        }
    }

    private fun loadFights() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val fakeFights = listOf(
                Fight("1", "Islam Makhachev", "Ilia Topuria", "UFC Fight Night", "2026-05-30", "")
            )
            
            try {
                val fights = getUpcomingFightsUseCase()
                if (fights.isEmpty()) {
                    _uiState.value = FightListUiState(fights = fakeFights)
                } else {
                    _uiState.value = FightListUiState(fights = fights)
                }
            } catch (e: Exception) {
                _uiState.value = FightListUiState(fights = fakeFights, error = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
