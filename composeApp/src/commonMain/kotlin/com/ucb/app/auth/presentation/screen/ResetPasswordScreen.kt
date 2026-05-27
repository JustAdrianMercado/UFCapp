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
import com.ucb.app.auth.presentation.composable.AuthTextField

@Composable
fun ResetPasswordScreen() {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        BackCircle()

        Spacer(modifier = Modifier.height(42.dp))

        Text("Set a new password", color = Color.Black, fontSize = 22.sp)
        Text("Create a new password. Ensure it differs from previous one for security", color = Color.Gray)

        Spacer(modifier = Modifier.height(28.dp))

        Text("Password", color = Color.Black)
        AuthTextField(
            value = password,
            placeholder = "Enter your new password",
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Confirm Password", color = Color.Black)
        AuthTextField(
            value = confirmPassword,
            placeholder = "Re-enter password",
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E8EE6)),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text("Update Password")
        }
    }
}