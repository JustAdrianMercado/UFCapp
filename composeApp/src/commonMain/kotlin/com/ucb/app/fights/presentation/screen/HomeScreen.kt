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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.fights.presentation.viewmodel.FightListViewModel
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.ucb.app.fights.presentation.state.FightListEvent

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
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.ucb.app.fights.presentation.state.FightListEffect.NavigateToFightDetail -> {
                    uriHandler.openUri(UFC_EVENTS_URL)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // --- TOP BAR ---
            AppTopBar(
                title = "CageX",
                backgroundColor = Color.Transparent,
                titleFontSize = 28,
                onSearchClick = onSearchClick
            )

            // --- MAIN EVENT HEADER ---
            mainFight?.let { fight ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clickable { viewModel.onEvent(FightListEvent.OnFightClick(fight.id)) }
                ) {
                    // Gradiente más profundo
                    Box(modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0f to Color(0xFF8B0000),
                            0.6f to Color(0xFF4B0000),
                            1f to Color.Black
                        )
                    ))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = stringResource(Res.string.ufc_fight_night).uppercase(),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = stringResource(Res.string.welterweight_bout).uppercase(),
                            color = Color.Gray,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        // Matchup con apellidos grandes
                        val name1 = fight.fighter1.split(" ").lastOrNull()?.uppercase() ?: ""
                        val name2 = fight.fighter2.split(" ").lastOrNull()?.uppercase() ?: ""
                        
                        Text(
                            text = name1,
                            color = Color.White,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 40.sp
                        )
                        Text(
                            text = "VS",
                            color = Color.Red,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = name2,
                            color = Color.White,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 40.sp
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = stringResource(Res.string.saturday_may_30).uppercase(),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(Res.string.time_7pm_et).uppercase(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
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
                    stringResource(Res.string.estelar_ufc_usa),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    stringResource(Res.string.more_info),
                    color = Color.Gray,
                    modifier = Modifier.clickable { onViewAllFights() }
                )
            }

            // --- CARDS DE NOTICIAS / PELEAS ---
            state.fights.take(3).forEach { fight ->
                NewsItem(
                    title = "${fight.fighter1} vs ${fight.fighter2}",
                    date = fight.date.split("T")[0],
                    time = if (fight.isMain) stringResource(Res.string.main_event) else stringResource(Res.string.undercard),
                    imageUrl = fight.imageUrl,
                    onClick = { viewModel.onEvent(FightListEvent.OnFightClick(fight.id)) }
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

private const val UFC_EVENTS_URL = "https://www.ufc.com/events"

@Composable
fun NewsItem(title: String, date: String, time: String, imageUrl: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 12.dp)
            .height(130.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(140.dp)
                .fillMaxHeight()
                .background(Color(0xFF1A1A1A)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                lineHeight = 24.sp
            )
            Text(
                text = date,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            if (time.isNotEmpty()) {
                Text(
                    text = time.uppercase(),
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.view_details),
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
