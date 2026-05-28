package com.ucb.app.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ucb.app.onboarding.data.datasource.LanguageDataSource
import com.ucb.app.onboarding.data.datasource.OnboardingPreferencesDataSource
import com.ucb.app.onboarding.data.datasource.OnboardingRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { FirebaseRemoteConfig.getInstance() }
    single { OnboardingRemoteDataSource(get()) }
    single { OnboardingPreferencesDataSource(androidContext()) }
    single { LanguageDataSource() }
}