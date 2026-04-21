package com.ucb.app.portafolio.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucb.app.portafolio.presentation.state.PortafolioEffect
import com.ucb.app.portafolio.presentation.state.PortafolioEvent
import com.ucb.app.portafolio.presentation.viewmodel.PortafolioViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.example.designsystem.components.input.BasicInput
import com.example.designsystem.components.button.PrimaryButton
import com.example.designsystem.components.theme.AppTheme

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
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .padding(16.dp)
    ) {

        // TÍTULO
        Text(
            text = "Firebase Portafolio",
            color = AppTheme.colors.primary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // INPUT PATH
        BasicInput(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = "Path",
            value = uiState.path,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnPathChanged(it))
            }
        )

        // INPUT VALUE
        BasicInput(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = "Valor",
            value = uiState.value,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnValueChanged(it))
            }
        )

        // BOTÓN GUARDAR
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnSaveClicked)
            },
            text = "Guardar"
        )

        // BOTÓN LEER
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnGetClicked)
            },
            text = "Leer"
        )

        HorizontalDivider()

        // LOADING
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        // MENSAJES
        uiState.successMessage?.let {
            Text("Éxito: $it")
        }

        uiState.errorMessage?.let {
            Text("Error: $it")
        }
    }
}
