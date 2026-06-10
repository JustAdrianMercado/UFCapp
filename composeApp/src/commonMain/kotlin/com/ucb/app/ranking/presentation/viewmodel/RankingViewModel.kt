package com.ucb.app.ranking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.ranking.domain.usecase.GetRankingsUseCase
import com.ucb.app.ranking.presentation.state.RankingEffect
import com.ucb.app.ranking.presentation.state.RankingEvent
import com.ucb.app.ranking.presentation.state.RankingUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingViewModel(
    private val getRankingsUseCase: GetRankingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState: StateFlow<RankingUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<RankingEffect>()
    val effect: SharedFlow<RankingEffect> = _effect.asSharedFlow()

    init {
        onEvent(RankingEvent.LoadRankings)
    }

    fun onEvent(event: RankingEvent) {
        when (event) {
            is RankingEvent.LoadRankings -> loadRankings()
            is RankingEvent.OnFighterClick -> {
                viewModelScope.launch {
                    _effect.emit(RankingEffect.NavigateToFighterDetail(event.fighterId))
                }
            }
        }
    }

    private fun loadRankings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val rankings = getRankingsUseCase()

                _uiState.value = RankingUiState(
                    isLoading = false,
                    rankings = rankings
                )
            } catch (e: Exception) {
                _uiState.value = RankingUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
