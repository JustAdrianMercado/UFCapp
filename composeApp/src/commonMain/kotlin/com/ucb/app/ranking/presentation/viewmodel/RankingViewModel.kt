package com.ucb.app.ranking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.ranking.domain.usecase.GetRankingsUseCase
import com.ucb.app.ranking.presentation.state.RankingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RankingViewModel(
    private val getRankingsUseCase: GetRankingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState: StateFlow<RankingUiState> = _uiState

    init {
        loadRankings()
    }

    private fun loadRankings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val rankings = getRankingsUseCase()

                _uiState.value = RankingUiState(
                    rankings = rankings
                )
            } catch (e: Exception) {
                _uiState.value = RankingUiState(
                    error = e.message
                )
            }
        }
    }
}