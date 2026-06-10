package com.ucb.app.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import com.ucb.app.auth.presentation.composable.AuthTextField
import com.ucb.app.auth.presentation.state.ResetPasswordEffect
import com.ucb.app.auth.presentation.state.ResetPasswordEvent
import com.ucb.app.auth.presentation.viewmodel.ResetPasswordViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: ResetPasswordViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ResetPasswordEffect.NavigateBack -> onNavigateBack()
                is ResetPasswordEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(24.dp),
        ) {
            BackCircle()

            Spacer(modifier = Modifier.height(42.dp))

            Text(stringResource(Res.string.reset_password_title), color = Color.Black, fontSize = 22.sp)
            Text(stringResource(Res.string.reset_password_desc), color = Color.Gray)

            Spacer(modifier = Modifier.height(28.dp))

            Text(stringResource(Res.string.password_label), color = Color.Black)
            AuthTextField(
                value = state.password,
                placeholder = stringResource(Res.string.enter_your_new_password)
            ) { viewModel.onEvent(ResetPasswordEvent.OnPasswordChanged(it)) }

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(Res.string.confirm_password), color = Color.Black)
            AuthTextField(
                value = state.confirmPassword,
                placeholder = stringResource(Res.string.re_enter_password)
            ) { viewModel.onEvent(ResetPasswordEvent.OnConfirmPasswordChanged(it)) }

            state.error?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(error, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { viewModel.onEvent(ResetPasswordEvent.OnUpdateClick) },
                enabled = !state.isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E8EE6)),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text(if (state.isLoading) "Updating..." else stringResource(Res.string.update_password_btn))
            }
        }
    }
}
