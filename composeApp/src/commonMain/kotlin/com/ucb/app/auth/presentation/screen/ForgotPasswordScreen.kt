package com.ucb.app.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import com.ucb.app.auth.presentation.composable.AuthTextField

@Composable
fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF120000), Color(0xFF8B0000)),
                )
            )
            .padding(24.dp)
    ) {
        BackCircle()

        Spacer(modifier = Modifier.height(42.dp))

        Text(stringResource(Res.string.forgot_password_title), color = Color.White, fontSize = 22.sp)
        Text(stringResource(Res.string.forgot_password_desc), color = Color.LightGray)

        Spacer(modifier = Modifier.height(28.dp))

        Text(stringResource(Res.string.your_email), color = Color.White)
        AuthTextField(
            value = email,
            placeholder = stringResource(Res.string.enter_your_email)
        ) { email = it }

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD40000)),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text(stringResource(Res.string.reset_password_btn))
        }
    }
}

@Composable
fun BackCircle() {
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(Color(0xFFEAEAEA), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text("<", color = Color.Black, fontSize = 24.sp)
    }
}