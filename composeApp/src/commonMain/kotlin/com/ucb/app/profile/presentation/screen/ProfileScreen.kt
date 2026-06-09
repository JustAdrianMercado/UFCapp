package com.ucb.app.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.profile.presentation.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateBack: () -> Unit,
    onEditProfile: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF120000), Color(0xFF8B0000))
                )
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "Error",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.profile != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "<", 
                                color = Color.White, 
                                fontSize = 28.sp,
                                modifier = Modifier.clickable { onNavigateBack() }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text("⚙", color = Color.White, fontSize = 24.sp)
                        }

                        Spacer(modifier = Modifier.height(34.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = state.profile?.pathUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = state.profile?.name ?: "",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = state.profile?.email ?: "",
                                    color = Color.White,
                                    fontSize = 13.sp
                                )

                                Button(
                                    onClick = onEditProfile,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE10600)
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Text("Edit Profile", fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(36.dp))

                        ProfileMenuItem("♡", "Favourites", {})
                        ProfileMenuItem("↓", "Downloads", {})

                        DividerLine()

                        ProfileMenuItem("◎", "Languages", {})
                        ProfileMenuItem("⌖", "Location", {})
                        ProfileMenuItem("▣", "Subscription", {})
                        ProfileMenuItem("▭", "Display", {})

                        DividerLine()

                        ProfileMenuItem("⌫", "Clear Cache", {})
                        ProfileMenuItem("◷", "Clear History", {})
                        ProfileMenuItem("↩", "Log Out", {})

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "App Version 1.2",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        AppBottomBar(
            currentRoute = NavRoute.Profile,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLive = onNavigateToLive,
            onNavigateToRanking = onNavigateToRanking,
            onNavigateToFighters = onNavigateToFighters,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: String,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, color = Color.White, fontSize = 22.sp)

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = text,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Text(">", color = Color.White)
    }
}

@Composable
fun DividerLine() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 10.dp),
        color = Color.White.copy(alpha = 0.5f)
    )
}
