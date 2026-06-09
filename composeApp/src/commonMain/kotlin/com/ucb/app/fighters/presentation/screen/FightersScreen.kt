package com.ucb.app.fighters.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.presentation.viewmodel.FightersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FightersScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: FightersViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AppTopBar(onSearchClick = onSearchClick)

        Text(
            text = "Fighters",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(18.dp)
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Text(
                        text = state.error ?: "Error",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.fighters) { fighter ->
                        FighterCard(fighter = fighter)
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            AppBottomBar(
                currentRoute = NavRoute.Fighters,
                onNavigateToHome = onNavigateToHome,
                onNavigateToLive = onNavigateToLive,
                onNavigateToRanking = onNavigateToRanking,
                onNavigateToFighters = { /* Already here */ },
                onNavigateToProfile = onNavigateToProfile
            )
        }
    }
}

@Composable
fun FighterCard(
    fighter: Fighter,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF121212)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = fighter.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fighter.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "\"${fighter.nickname}\"",
                    color = Color(0xFFE10600),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = fighter.division,
                    color = Color.LightGray,
                    fontSize = 13.sp
                )

                Text(
                    text = fighter.record,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


