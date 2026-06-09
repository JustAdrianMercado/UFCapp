package com.ucb.app.onboarding.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.onboarding.presentation.state.OnboardingEffect
import com.ucb.app.onboarding.presentation.state.OnboardingEvent
import com.ucb.app.onboarding.presentation.viewmodel.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    onNavigateHome: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingEffect.NavigateToHome -> onNavigateHome()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, Color(0xFF160000), Color(0xFF8B0000))
                )
            )
            .padding(22.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Error",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.currentPage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CageX",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = {
                                viewModel.onEvent(OnboardingEvent.OnSkipClick)
                            }
                        ) {
                            Text("Omitir", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    AsyncImage(
                        model = state.currentPage?.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = state.currentPage?.title.orEmpty(),
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = state.currentPage?.description.orEmpty(),
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(state.pages.size) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(if (index == state.currentIndex) 12.dp else 8.dp)
                                    .background(
                                        color = if (index == state.currentIndex) {
                                            Color(0xFFE10600)
                                        } else {
                                            Color.White.copy(alpha = 0.4f)
                                        },
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!state.isFirstPage) {
                            OutlinedButton(
                                onClick = {
                                    viewModel.onEvent(OnboardingEvent.OnPreviousClick)
                                },
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Anterior", color = Color.White)
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Button(
                            onClick = {
                                if (state.isLastPage) {
                                    viewModel.onEvent(OnboardingEvent.OnStartClick)
                                } else {
                                    viewModel.onEvent(OnboardingEvent.OnNextClick)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE10600)
                            ),
                            shape = RoundedCornerShape(28.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (state.isLastPage) "Iniciar" else "Siguiente",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}