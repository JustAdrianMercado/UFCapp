package com.ucb.app.di

import com.ucb.app.auth.presentation.viewmodel.LoginViewModel
import com.ucb.app.auth.presentation.viewmodel.ResetPasswordViewModel
import com.ucb.app.fighters.presentation.viewmodel.FightersViewModel
import com.ucb.app.fights.presentation.viewmodel.FightListViewModel
import com.ucb.app.live.presentation.viewmodel.LiveViewModel
import com.ucb.app.onboarding.presentation.viewmodel.OnboardingViewModel
import com.ucb.app.profile.presentation.viewmodel.ProfileEditViewModel
import com.ucb.app.profile.presentation.viewmodel.ProfileViewModel
import com.ucb.app.ranking.presentation.viewmodel.RankingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::FightListViewModel)
    viewModelOf(::RankingViewModel)
    viewModelOf(::LiveViewModel)
    viewModelOf(::FightersViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::OnboardingViewModel)
}
