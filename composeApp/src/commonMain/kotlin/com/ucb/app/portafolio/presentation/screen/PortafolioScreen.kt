package com.ucb.app.portafolio.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.button.PrimaryButton
import com.example.designsystem.components.input.BasicInput
import com.example.designsystem.components.theme.AppTheme
import com.ucb.app.portafolio.presentation.state.PortafolioEffect
import com.ucb.app.portafolio.presentation.state.PortafolioEvent
import com.ucb.app.portafolio.presentation.viewmodel.PortafolioViewModel
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.button_read
import kotlinproject.composeapp.generated.resources.button_save
import kotlinproject.composeapp.generated.resources.error_message
import kotlinproject.composeapp.generated.resources.label_path
import kotlinproject.composeapp.generated.resources.label_value
import kotlinproject.composeapp.generated.resources.portfolio_title
import kotlinproject.composeapp.generated.resources.success_message
import org.jetbrains.compose.resources.stringResource
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
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.portfolio_title),
            color = AppTheme.colors.primary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        BasicInput(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = stringResource(Res.string.label_path),
            value = uiState.path,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnPathChanged(it))
            }
        )

        BasicInput(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = stringResource(Res.string.label_value),
            value = uiState.value,
            onValueChange = {
                viewModel.onEvent(PortafolioEvent.OnValueChanged(it))
            }
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnSaveClicked)
            },
            text = stringResource(Res.string.button_save)
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {
                viewModel.onEvent(PortafolioEvent.OnGetClicked)
            },
            text = stringResource(Res.string.button_read)
        )

        HorizontalDivider()

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.successMessage?.let { message ->
            Text(
                text = stringResource(Res.string.success_message, message)
            )
        }

        uiState.errorMessage?.let { message ->
            Text(
                text = stringResource(Res.string.error_message, message)
            )
        }
    }
}