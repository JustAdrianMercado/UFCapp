package com.ucb.app.config.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucb.app.config.presentation.viewmodel.ConfigViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Sincronización inicial con caché local",
            style = MaterialTheme.typography.titleLarge
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        Text(
            text = if (state.value.isNotBlank()) {
                "Valor guardado: ${state.value}"
            } else {
                "No hay valor guardado todavía"
            }
        )

        state.error?.let {
            Text(
                text = "Error: $it",
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(onClick = { viewModel.syncInitialConfig() }) {
            Text("Sincronizar")
        }
    }
}