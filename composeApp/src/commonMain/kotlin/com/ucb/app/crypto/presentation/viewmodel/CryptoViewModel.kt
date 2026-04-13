package com.ucb.app.crypto.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.app.crypto.domain.usecase.GetCryptosUseCase
import com.ucb.app.crypto.presentation.state.CryptoEffect
import com.ucb.app.crypto.presentation.state.CryptoEvent
import com.ucb.app.crypto.presentation.state.CryptoState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoViewModel(
    private val getCryptosUseCase: GetCryptosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CryptoState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CryptoEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: CryptoEvent) {
        when (event) {
            CryptoEvent.OnLoad -> loadCrypto()
            CryptoEvent.OnRefresh -> loadCrypto()
        }
    }

    private fun loadCrypto() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val cryptoList = getCryptosUseCase()

                _state.update {
                    it.copy(
                        isLoading = false,
                        cryptos = cryptoList,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "No se pudo cargar las criptomonedas"

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }

                _effect.send(CryptoEffect.ShowError(errorMessage))
            }
        }
    }
}