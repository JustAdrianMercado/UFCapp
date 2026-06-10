package com.ucb.app.ranking.presentation.viewmodel

import app.cash.turbine.test
import com.ucb.app.ranking.domain.model.FighterRanking
import com.ucb.app.ranking.domain.repository.RankingRepository
import com.ucb.app.ranking.domain.usecase.GetRankingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RankingViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load rankings from use case`() = runTest {
        // Given
        val rankings = listOf(
            FighterRanking("1", 1, "Fighter 1", "Division", "", 10, 0)
        )
        val repository = object : RankingRepository {
            override suspend fun getRankings(): List<FighterRanking> = rankings
        }
        val useCase = GetRankingsUseCase(repository)

        // When
        val viewModel = RankingViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(rankings, state.rankings)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `when use case fails should update state with error`() = runTest {
        // Given
        val errorMessage = "Failed to load rankings"
        val repository = object : RankingRepository {
            override suspend fun getRankings(): List<FighterRanking> = throw Exception(errorMessage)
        }
        val useCase = GetRankingsUseCase(repository)

        // When
        val viewModel = RankingViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }
}
