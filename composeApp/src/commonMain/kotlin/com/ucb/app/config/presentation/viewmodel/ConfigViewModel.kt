package com.ucb.app.config.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.config.domain.usecase.GetLocalConfigUseCase
import com.ucb.app.config.domain.usecase.SyncInitialConfigUseCase
import com.ucb.app.config.presentation.state.ConfigUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConfigViewModel(
    private val getLocalConfigUseCase: GetLocalConfigUseCase,
    private val syncInitialConfigUseCase: SyncInitialConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigUiState(isLoading = true))
    val uiState: StateFlow<ConfigUiState> = _uiState.asStateFlow()

    private val configKey = "welcome_message"

    init {
        observeLocalConfig()
        syncInitialConfig()
    }

    private fun observeLocalConfig() {
        getLocalConfigUseCase(configKey)
            .onEach { value ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    value = value.orEmpty(),
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }

    fun syncInitialConfig() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            runCatching {
                syncInitialConfigUseCase(configKey)
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = throwable.message
                )
            }
        }
    }
}