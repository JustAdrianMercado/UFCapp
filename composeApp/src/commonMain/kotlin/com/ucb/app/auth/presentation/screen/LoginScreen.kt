package com.ucb.app.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import com.ucb.app.auth.presentation.composable.AuthTextField
import com.ucb.app.auth.presentation.state.LoginEffect
import com.ucb.app.auth.presentation.state.LoginEvent
import com.ucb.app.auth.presentation.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> onNavigateHome()
                is LoginEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF160000), Color(0xFF860000), Color(0xFFE10600))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 38.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    color = Color.White,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(34.dp))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(78.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.isRegisterMode) {
                    FieldLabel("Name/s")
                    AuthTextField(
                        value = state.firstName,
                        placeholder = "",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnFirstNameChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    FieldLabel("Last name/s")
                    AuthTextField(
                        value = state.lastName,
                        placeholder = "",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnLastNameChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    FieldLabel("Username")
                    AuthTextField(
                        value = state.username,
                        placeholder = "",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnUsernameChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }

                FieldLabel("Email")
                AuthTextField(
                    value = state.email,
                    placeholder = "",
                    onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (state.isRegisterMode) {
                    FieldLabel("Phone number")
                    AuthTextField(
                        value = state.phoneNumber,
                        placeholder = "",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPhoneNumberChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    FieldLabel("Birthday")
                    AuthTextField(
                        value = state.birthday,
                        placeholder = "DD/MM/YYYY",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnBirthdayChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    FieldLabel("Gender")
                    AuthTextField(
                        value = state.gender,
                        placeholder = "",
                        onValueChange = { viewModel.onEvent(LoginEvent.OnGenderChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }

                FieldLabel(stringResource(Res.string.password_label))
                AuthTextField(
                    value = state.password,
                    placeholder = "",
                    onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) }
                )

                state.error?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(error, color = Color.White, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { viewModel.onEvent(LoginEvent.OnClick) },
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7A0000),
                        disabledContainerColor = Color(0xFF7A0000).copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = if (state.isLoading) "Please wait..." else if (state.isRegisterMode) "Register" else stringResource(Res.string.log_in_btn),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                TextButton(onClick = { viewModel.onEvent(LoginEvent.OnToggleModeClick) }) {
                    Text(
                        text = if (state.isRegisterMode) "Already have an account? Log in" else "New here? Register",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.FieldLabel(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 12.sp,
        modifier = Modifier.align(Alignment.Start)
    )
}
