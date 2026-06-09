package com.ucb.app.fights.presentation.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.fights.presentation.viewmodel.FightListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onViewAllFights: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: FightListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val mainFight = state.fights.find { it.isMain } ?: state.fights.firstOrNull()

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // --- TOP BAR ---
            AppTopBar(
                backgroundColor = Color.Transparent,
                titleFontSize = 28,
                onSearchClick = onSearchClick
            )

            // --- MAIN EVENT HEADER ---
            mainFight?.let { fight ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clickable { onViewAllFights() }
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(listOf(Color(0xFFB11212), Color.Black))
                    ))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("UFC FIGHT NIGHT", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("WELTERWEIGHT BOUT", color = Color.Gray, fontSize = 10.sp)
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val f1 = fight.fighter1.split(" ").lastOrNull()?.uppercase() ?: ""
                            val f2 = fight.fighter2.split(" ").lastOrNull()?.uppercase() ?: ""
                            Text(
                                text = f1,
                                color = Color.White,
                                fontSize = 38.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(" VS ", color = Color.Red, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = f2,
                                color = Color.White,
                                fontSize = 38.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "SATURDAY, MAY 30",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("7 PM ET", color = Color.White, fontSize = 14.sp)
                    }
                }
            }

            // --- SECCIÓN "MÁS INFORMACIÓN" ---
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    modifier = Modifier.width(40.dp),
                    thickness = 2.dp,
                    color = Color.Red
                )
                Text(
                    "Estelar en UFC USA",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    "Más información",
                    color = Color.Gray,
                    modifier = Modifier.clickable { onViewAllFights() }
                )
            }

            // --- CARDS DE NOTICIAS / PELEAS ---
            state.fights.take(3).forEach { fight ->
                NewsItem(
                    title = "${fight.fighter1} vs ${fight.fighter2}",
                    date = fight.date.split("T")[0],
                    time = if (fight.isMain) "EVENTO ESTELAR" else "Cartelera",
                    imageUrl = fight.imageUrl,
                    onClick = { onViewAllFights() }
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        // --- BOTTOM NAVIGATION BAR ---
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AppBottomBar(
                currentRoute = NavRoute.Home,
                onNavigateToHome = { /* Already here */ },
                onNavigateToLive = onNavigateToLive,
                onNavigateToRanking = onNavigateToRanking,
                onNavigateToFighters = onNavigateToFighters,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    }
}

@Composable
fun NewsItem(title: String, date: String, time: String, imageUrl: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(120.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.size(120.dp).background(Color(0xFF1A1A1A)),
            contentScale = ContentScale.Fit
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 2)
            Text(date, color = Color.Gray, fontSize = 14.sp)
            if (time.isNotEmpty()) Text(time, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text("Ver detalles", color = Color.White, fontSize = 12.sp)
        }
    }
}

