package com.ucb.app.fighters.domain.usecase

import com.ucb.app.fighters.domain.model.Fighter
import com.ucb.app.fighters.domain.repository.FighterRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetFightersUseCaseTest {

    @Test
    fun `when invoke then return fighters from repository`() = runTest {
        // Given
        val expectedFighters = listOf(
            Fighter("1", "Name", "Nickname", "Division", "0-0", "")
        )
        val repository = object : FighterRepository {
            override suspend fun getFighters(): List<Fighter> = expectedFighters
        }
        val useCase = GetFightersUseCase(repository)

        // When
        val result = useCase()

        // Then
        assertEquals(expectedFighters, result)
    }
}
