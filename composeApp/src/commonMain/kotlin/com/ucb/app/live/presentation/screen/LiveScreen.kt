package com.ucb.app.live.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.live.domain.model.LiveEvent
import com.ucb.app.live.presentation.viewmodel.LiveViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LiveScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: LiveViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AppTopBar(onSearchClick = onSearchClick)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Donde Ver",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                ParamountBox(onClick = {})

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000)
                    )
                ) {
                    Text(
                        text = "Mira en Paramount+",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Boletos",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))
            }

            items(state.events) { event ->
                LiveEventItem(event = event)
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "A la venta - Público",
                    color = Color(0xFFB22222),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000)
                    )
                ) {
                    Text(
                        text = "Disponible",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            AppBottomBar(
                currentRoute = NavRoute.Live,
                onNavigateToHome = onNavigateToHome,
                onNavigateToLive = { /* Already here */ },
                onNavigateToRanking = onNavigateToRanking,
                onNavigateToFighters = onNavigateToFighters,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    }
}


@Composable
fun ParamountBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color(0xFF006DFF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Paramount+",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LiveEventItem(
    event: LiveEvent,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 14.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = event.date,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = event.time,
            color = Color.White,
            fontSize = 15.sp
        )

        Text(
            text = event.title,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

