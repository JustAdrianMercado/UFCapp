package com.ucb.app.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.app.auth.presentation.composable.AuthTextField
import com.ucb.app.auth.presentation.state.LoginEvent
import com.ucb.app.auth.presentation.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF120000), Color(0xFF8B0000))
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CageX",
                color = Color.White,
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text("Username", color = Color.White, modifier = Modifier.align(Alignment.Start))
            AuthTextField(
                value = state.email,
                placeholder = "",
                onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("Password", color = Color.White, modifier = Modifier.align(Alignment.Start))
            AuthTextField(
                value = state.password,
                placeholder = "",
                onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChanged(it)) }
            )

            Spacer(modifier = Modifier.height(34.dp))

            Button(
                onClick = { viewModel.onEvent(LoginEvent.OnClick) },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7A0000)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text("Log In", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}