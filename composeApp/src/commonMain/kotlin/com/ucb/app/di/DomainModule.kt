package com.ucb.app.di

import com.ucb.app.config.domain.usecase.GetLocalConfigUseCase
import com.ucb.app.config.domain.usecase.SyncInitialConfigUseCase
import com.ucb.app.auth.domain.usecase.DoLoginUseCase
import com.ucb.app.auth.domain.usecase.DoRegisterUseCase
import com.ucb.app.auth.domain.usecase.UpdatePasswordUseCase
import com.ucb.app.event.domain.usecase.RegisterAppEventUseCase
import com.ucb.app.fighters.domain.usecase.GetFightersUseCase
import com.ucb.app.fights.domain.usecase.GetUpcomingFightsUseCase
import com.ucb.app.live.domain.usecase.GetLiveEventsUseCase
import com.ucb.app.onboarding.domain.usecase.GetOnboardingCompletedUseCase
import com.ucb.app.onboarding.domain.usecase.GetOnboardingPagesUseCase
import com.ucb.app.onboarding.domain.usecase.SaveOnboardingCompletedUseCase
import com.ucb.app.portafolio.domain.usecase.GetPortafolioDataUseCase
import com.ucb.app.portafolio.domain.usecase.SavePortafolioDataUseCase
import com.ucb.app.profile.domain.usecase.EditProfileUseCase
import com.ucb.app.profile.domain.usecase.GetProfileUseCase
import com.ucb.app.ranking.domain.usecase.GetRankingsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::SavePortafolioDataUseCase)
    singleOf(::GetPortafolioDataUseCase)

    singleOf(::GetLocalConfigUseCase)
    singleOf(::SyncInitialConfigUseCase)
    singleOf(::RegisterAppEventUseCase)
    singleOf(::GetUpcomingFightsUseCase)
    singleOf(::GetRankingsUseCase)
    singleOf(::GetLiveEventsUseCase)
    singleOf(::GetFightersUseCase)
    singleOf(::GetProfileUseCase)
    singleOf(::EditProfileUseCase)
    singleOf(::DoLoginUseCase)
    singleOf(::DoRegisterUseCase)
    singleOf(::UpdatePasswordUseCase)

    single { GetOnboardingPagesUseCase(get()) }
    single { GetOnboardingCompletedUseCase(get()) }
    single { SaveOnboardingCompletedUseCase(get()) }
}
