package com.ucb.app.fights.domain.usecase

import com.ucb.app.fights.domain.model.Fight
import com.ucb.app.fights.domain.repository.FightRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUpcomingFightsUseCaseTest {

    @Test
    fun `when invoke then return fights from repository`() = runTest {
        // Given
        val expectedFights = listOf(
            Fight("1", "Fighter 1", "Fighter 2", "Event", "2024-01-01", "")
        )
        val repository = object : FightRepository {
            override suspend fun getUpcomingFights(): List<Fight> = expectedFights
        }
        val useCase = GetUpcomingFightsUseCase(repository)

        // When
        val result = useCase()

        // Then
        assertEquals(expectedFights, result)
    }
}
