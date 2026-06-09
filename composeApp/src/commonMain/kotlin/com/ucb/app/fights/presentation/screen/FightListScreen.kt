package com.ucb.app.fights.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.fights.presentation.viewmodel.FightListViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.ucb.app.fights.domain.model.Fight

@Composable
fun FightListScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: FightListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AppTopBar(
            title = "Peleas",
            onSearchClick = onSearchClick
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center).padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }

                state.fights.isEmpty() -> {
                    Text(
                        text = "No se encontraron peleas para esta fecha.\nRevisa tu conexión o API Key.",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center).padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            Text(
                                text = "PRÓXIMAS PELEAS",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        items(state.fights) { fight ->
                            FightCard(fight = fight)
                        }
                    }
                }
            }
        }

        AppBottomBar(
            currentRoute = NavRoute.Fights,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLive = onNavigateToLive,
            onNavigateToRanking = onNavigateToRanking,
            onNavigateToFighters = onNavigateToFighters,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}

@Composable
fun FightCard(
    fight: Fight,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        )
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                AsyncImage(
                    model = fight.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )
                
                Text(
                    text = fight.eventName,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${fight.fighter1.uppercase()} vs ${fight.fighter2.uppercase()}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Text(
                    text = fight.date.split("T")[0],
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
