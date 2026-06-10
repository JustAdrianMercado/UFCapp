package com.ucb.app.ranking.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.app.navigation.AppBottomBar
import com.ucb.app.navigation.AppTopBar
import com.ucb.app.navigation.NavRoute
import com.ucb.app.ranking.presentation.state.RankingEvent
import com.ucb.app.ranking.presentation.viewmodel.RankingViewModel
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RankingScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: RankingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.ucb.app.ranking.presentation.state.RankingEffect.NavigateToFighterDetail -> {
                    // TODO: Implement navigation
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

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp)
        ) {
            item {
                Text(
                    text = stringResource(Res.string.athlete_rankings),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RankingColumn(
                        title = stringResource(Res.string.mens_p4p_top_rank),
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f),
                        onFighterClick = { id -> viewModel.onEvent(RankingEvent.OnFighterClick(id)) }
                    )

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(330.dp)
                            .background(Color.White)
                    )

                    RankingColumn(
                        title = stringResource(Res.string.flyweight),
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f),
                        onFighterClick = { id -> viewModel.onEvent(RankingEvent.OnFighterClick(id)) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RankingColumn(
                        title = stringResource(Res.string.bantamweight),
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f),
                        onFighterClick = { id -> viewModel.onEvent(RankingEvent.OnFighterClick(id)) }
                    )

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(330.dp)
                            .background(Color.White)
                    )

                    RankingColumn(
                        title = stringResource(Res.string.featherweight),
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f),
                        onFighterClick = { id -> viewModel.onEvent(RankingEvent.OnFighterClick(id)) }
                    )
                }
            }
        }

        AppBottomBar(
            currentRoute = NavRoute.Ranking,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLive = onNavigateToLive,
            onNavigateToRanking = { /* Already here */ },
            onNavigateToFighters = onNavigateToFighters,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}

@Composable
fun RankingColumn(
    title: String,
    fighters: List<com.ucb.app.ranking.domain.model.FighterRanking>,
    modifier: Modifier = Modifier,
    onFighterClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = title,
            color = Color(0xFFE10600),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        repeat(15) { index ->
            val fighter = fighters.getOrNull(index % fighters.size)

            Text(
                text = "${index + 1} ${fighter?.fighterName ?: "Fighter Name"}",
                color = Color.White,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .clickable { fighter?.let { onFighterClick(it.id) } }
            )
        }
    }
}
