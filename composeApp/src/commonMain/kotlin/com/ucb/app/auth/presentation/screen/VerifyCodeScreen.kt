package com.ucb.app.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerifyCodeScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF120000), Color(0xFF8B0000)),
                )
            )
            .padding(22.dp)
    ) {
        Column {
            BackCircle()

            Spacer(modifier = Modifier.height(36.dp))

            Text("Check your email", color = Color.White, fontSize = 22.sp)
            Text("We sent a reset link to your email", color = Color.LightGray)

            Spacer(modifier = Modifier.height(30.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(5) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD40000)),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text("Verify Code")
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text("Haven't got the email yet? Resend email", color = Color.White)
        }
    }
}