package com.ucb.app.portafolio.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucb.app.portafolio.presentation.state.PortafolioEffect
import com.ucb.app.portafolio.presentation.state.PortafolioEvent
import com.ucb.app.portafolio.presentation.viewmodel.PortafolioViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PortafolioScreen(
    viewModel: PortafolioViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PortafolioEffect.ShowMessage -> {
                    println(effect.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Guardar y leer dato en Firebase")

        OutlinedTextField(
            value = uiState.path,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnPathChanged(it))
            },
            label = { Text("Path") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.value,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnValueChanged(it))
            },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnSaveClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        Button(
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnGetClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Leer")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.successMessage?.let {
            Text("Éxito: $it")
        }

        uiState.errorMessage?.let {
            Text("Error: $it")
        }
    }
}