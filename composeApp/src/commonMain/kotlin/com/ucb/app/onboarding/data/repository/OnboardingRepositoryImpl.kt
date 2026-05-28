package com.ucb.app.onboarding.data.repository

import com.ucb.app.onboarding.data.datasource.LanguageDataSource
import com.ucb.app.onboarding.data.datasource.OnboardingPreferencesDataSource
import com.ucb.app.onboarding.data.datasource.OnboardingRemoteDataSource
import com.ucb.app.onboarding.data.mapper.toDomain
import com.ucb.app.onboarding.domain.model.OnboardingPage
import com.ucb.app.onboarding.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val remoteDataSource: OnboardingRemoteDataSource,
    private val preferencesDataSource: OnboardingPreferencesDataSource,
    private val languageDataSource: LanguageDataSource
) : OnboardingRepository {

    override suspend fun getPages(): List<OnboardingPage> {
        val language = languageDataSource.getLanguage()
        val config = remoteDataSource.getConfig()

        return config.map { dto ->
            dto.toDomain(language)
        }
    }

    override suspend fun saveCompleted() {
        preferencesDataSource.saveCompleted()
    }

    override suspend fun isCompleted(): Boolean {
        return preferencesDataSource.isCompleted()
    }
}