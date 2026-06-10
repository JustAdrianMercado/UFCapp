package com.ucb.app.di

import com.ucb.app.auth.data.repository.AuthenticationRepositoryImpl
import com.ucb.app.auth.domain.repository.AuthenticationRepository
import com.ucb.app.fighters.data.repository.FighterRepositoryImpl
import com.ucb.app.fighters.data.service.FighterApiService
import com.ucb.app.fighters.domain.repository.FighterRepository
import com.ucb.app.fights.data.repository.FightRepositoryImpl
import com.ucb.app.fights.data.service.FightApiService
import com.ucb.app.fights.domain.repository.FightRepository
import com.ucb.app.live.data.repository.LiveRepositoryImpl
import com.ucb.app.live.domain.repository.LiveRepository
import com.ucb.app.onboarding.data.repository.OnboardingRepositoryImpl
import com.ucb.app.onboarding.domain.repository.OnboardingRepository
import com.ucb.app.portafolio.data.datasource.FirebaseManager
import com.ucb.app.portafolio.data.repository.PortafolioRepositoryImpl
import com.ucb.app.portafolio.domain.repository.PortafolioRepository
import com.ucb.app.profile.data.repository.ProfileRepositoryImpl
import com.ucb.app.profile.domain.repository.ProfileRepository
import com.ucb.app.ranking.data.repository.RankingRepositoryImpl
import com.ucb.app.ranking.domain.repository.RankingRepository
import com.ucb.app.session.SessionManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    singleOf(::FirebaseManager)
    singleOf(::SessionManager)
    singleOf(::PortafolioRepositoryImpl).bind<PortafolioRepository>()

    singleOf(::FightApiService)
    single { FightRepositoryImpl(get()) }.bind<FightRepository>()

    singleOf(::RankingRepositoryImpl).bind<RankingRepository>()
    singleOf(::LiveRepositoryImpl).bind<LiveRepository>()

    singleOf(::FighterApiService)
    singleOf(::FighterRepositoryImpl).bind<FighterRepository>()

    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()

    singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()

    single<OnboardingRepository> {
        OnboardingRepositoryImpl(get(), get(), get())
    }
}
