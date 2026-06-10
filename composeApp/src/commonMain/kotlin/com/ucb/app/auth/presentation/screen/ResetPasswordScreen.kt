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

@Composable
fun ResetPasswordScreen() {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
    ) {
        BackCircle()

        Spacer(modifier = Modifier.height(42.dp))

        Text(stringResource(Res.string.reset_password_title), color = Color.Black, fontSize = 22.sp)
        Text(stringResource(Res.string.reset_password_desc), color = Color.Gray)

        Spacer(modifier = Modifier.height(28.dp))

        Text(stringResource(Res.string.password_label), color = Color.Black)
        AuthTextField(
            value = password,
            placeholder = stringResource(Res.string.enter_your_new_password)
        ) { password = it }

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(Res.string.confirm_password), color = Color.Black)
        AuthTextField(
            value = confirmPassword,
            placeholder = stringResource(Res.string.re_enter_password)
        ) { confirmPassword = it }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E8EE6)),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text(stringResource(Res.string.update_password_btn))
        }
    }
}