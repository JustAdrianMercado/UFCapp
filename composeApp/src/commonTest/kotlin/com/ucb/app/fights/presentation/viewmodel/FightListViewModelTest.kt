package com.ucb.app.fights.presentation.viewmodel

import app.cash.turbine.test
import com.ucb.app.fights.domain.model.Fight
import com.ucb.app.fights.domain.repository.FightRepository
import com.ucb.app.fights.domain.usecase.GetUpcomingFightsUseCase
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
class FightListViewModelTest {

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
    fun `init should load fights from use case`() = runTest {
        // Given
        val fights = listOf(
            Fight("1", "Fighter 1", "Fighter 2", "Event", "2024-01-01", "")
        )
        val repository = object : FightRepository {
            override suspend fun getUpcomingFights(): List<Fight> = fights
        }
        val useCase = GetUpcomingFightsUseCase(repository)

        // When
        val viewModel = FightListViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(fights, state.fights)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `when use case fails should update state with error`() = runTest {
        // Given
        val errorMessage = "Network Error"
        val repository = object : FightRepository {
            override suspend fun getUpcomingFights(): List<Fight> = throw Exception(errorMessage)
        }
        val useCase = GetUpcomingFightsUseCase(repository)

        // When
        val viewModel = FightListViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }
}
