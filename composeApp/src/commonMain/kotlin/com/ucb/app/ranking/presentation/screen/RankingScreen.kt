package com.ucb.app.ranking.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucb.app.ranking.presentation.viewmodel.RankingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RankingScreen(
    viewModel: RankingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        RankingTopBar()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp)
        ) {
            item {
                Text(
                    text = "Clasificación de los atletas",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RankingColumn(
                        title = "Men's Pound-for-\nPound Top Rank",
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(330.dp)
                            .background(Color.White)
                    )

                    RankingColumn(
                        title = "Peso mosca",
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RankingColumn(
                        title = "Peso gallo",
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(330.dp)
                            .background(Color.White)
                    )

                    RankingColumn(
                        title = "Peso pluma",
                        fighters = state.rankings,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        RankingBottomBar()
    }
}

@Composable
fun RankingTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(Color(0xFFD40000))
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "CageX",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "⌕",
            color = Color.White,
            fontSize = 28.sp
        )
    }
}

@Composable
fun RankingColumn(
    title: String,
    fighters: List<com.ucb.app.ranking.domain.model.FighterRanking>,
    modifier: Modifier = Modifier
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
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun RankingBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFD40000))
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Home", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Live", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Rankings", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Fighters", color = Color.White, fontWeight = FontWeight.Bold)
        Text("☆", color = Color.White, fontSize = 24.sp)
    }
}