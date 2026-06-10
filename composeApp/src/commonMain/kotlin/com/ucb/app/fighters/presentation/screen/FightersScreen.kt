package com.ucb.app.fighters.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.presentation.state.FightersEvent
import com.ucb.app.fighters.presentation.viewmodel.FightersViewModel
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.fighters_title
import org.jetbrains.compose.resources.stringResource
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
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.ucb.app.fighters.presentation.state.FightersEffect.NavigateToFighterDetail -> {
                    uriHandler.openUri(UFC_ATHLETES_URL)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AppTopBar(onSearchClick = onSearchClick)

        Text(
            text = stringResource(Res.string.fighters_title),
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
        )

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(FightersEvent.OnSearchQueryChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            placeholder = { Text("Search fighters...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.Red,
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedContainerColor = Color(0xFF121212),
                unfocusedContainerColor = Color(0xFF121212)
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.error}",
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Button(
                            onClick = { viewModel.onEvent(FightersEvent.LoadFighters) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            state.filteredFighters.isEmpty() -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Text(
                        text = if (state.searchQuery.isEmpty()) "No fighters found" else "No results for \"${state.searchQuery}\"",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(state.filteredFighters) { fighter ->
                        FighterCard(
                            fighter = fighter,
                            onClick = { viewModel.onEvent(FightersEvent.OnFighterClick(fighter.id)) }
                        )
                    }
                }
            }
        }

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

private const val UFC_ATHLETES_URL = "https://www.ufc.com/athletes/all"

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
