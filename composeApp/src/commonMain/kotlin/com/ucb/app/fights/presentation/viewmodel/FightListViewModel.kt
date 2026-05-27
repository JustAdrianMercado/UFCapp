package com.ucb.app.fights.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.fights.domain.model.Fight
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
        println("DEBUG: Iniciando FightListViewModel")
        loadFights()
    }

    private fun loadFights() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // PRUEBA: Datos manuales para descartar error de UI
            val fakeFights = listOf(
                Fight("1", "PRUEBA 1", "PRUEBA 2", "UFC EVENT TEST", "2026-05-30", "")
            )
            
            try {
                // Intentamos los reales, pero si fallan o tardan, mostramos los fake
                val fights = getUpcomingFightsUseCase()
                if (fights.isEmpty()) {
                    _uiState.value = FightListUiState(fights = fakeFights)
                } else {
                    _uiState.value = FightListUiState(fights = fights)
                }
            } catch (e: Exception) {
                println("DEBUG ERROR: ${e.message}")
                _uiState.value = FightListUiState(fights = fakeFights, error = e.message)
            }
        }
    }
}
