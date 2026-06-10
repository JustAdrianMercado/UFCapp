package com.ucb.app.fighters.presentation.viewmodel

import app.cash.turbine.test
import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.domain.repository.FighterRepository
import com.ucb.app.fighters.domain.usecase.GetFightersUseCase
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
class FightersViewModelTest {

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
    fun `init should load fighters from use case`() = runTest {
        // Given
        val fighters = listOf(
            Fighter("1", "Fighter 1", "Nickname", "Division", "0-0", "")
        )
        val repository = object : FighterRepository {
            override suspend fun getFighters(): List<Fighter> = fighters
        }
        val useCase = GetFightersUseCase(repository)

        // When
        val viewModel = FightersViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(fighters, state.fighters)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `when use case fails should update state with error`() = runTest {
        // Given
        val errorMessage = "Error loading fighters"
        val repository = object : FighterRepository {
            override suspend fun getFighters(): List<Fighter> = throw Exception(errorMessage)
        }
        val useCase = GetFightersUseCase(repository)

        // When
        val viewModel = FightersViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }
}
