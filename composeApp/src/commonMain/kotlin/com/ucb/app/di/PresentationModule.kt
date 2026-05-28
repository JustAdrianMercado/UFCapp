package com.ucb.app.di

import com.ucb.app.config.presentation.viewmodel.ConfigViewModel
import com.ucb.app.counter.presentation.viewmodel.CounterViewModel
import com.ucb.app.country.presentation.viewmodel.CountryViewModel
import com.ucb.app.crypto.presentation.viewmodel.CryptoViewModel
import com.ucb.app.fakestore.presentation.viewmodel.FakeStoreViewModel
import com.ucb.app.github.presentation.viewmodel.GithubViewModel
import com.ucb.app.increment.presentation.viewmodel.IncrementViewModel
import com.ucb.app.movie.presentation.viewmodel.MovieViewModel
import com.ucb.app.auth.presentation.viewmodel.LoginViewModel
import com.ucb.app.portafolio.presentation.viewmodel.PortafolioViewModel
import com.ucb.app.product_detail.presentation.viewmodel.ProductDetailViewModel
import com.ucb.app.signin.presentation.viewmodel.SigninViewModel
import org.koin.dsl.module
import com.ucb.app.fights.presentation.viewmodel.FightListViewModel
import com.ucb.app.ranking.presentation.viewmodel.RankingViewModel
import com.ucb.app.live.presentation.viewmodel.LiveViewModel
import com.ucb.app.fighters.presentation.viewmodel.FightersViewModel
import com.ucb.app.profile.presentation.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import com.ucb.app.onboarding.presentation.viewmodel.OnboardingViewModel


val presentationModule = module {
    viewModelOf(::ProductDetailViewModel)
    viewModelOf(::CounterViewModel)
    viewModelOf(::IncrementViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::GithubViewModel)
    viewModelOf(::SigninViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::CryptoViewModel)
    viewModelOf(::FakeStoreViewModel)
    viewModelOf(::CountryViewModel)
    viewModelOf(::PortafolioViewModel)
    viewModelOf(::ConfigViewModel)
    viewModelOf(::FightListViewModel)
    viewModelOf(::RankingViewModel)
    viewModelOf(::LiveViewModel)
    viewModelOf(::FightersViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::OnboardingViewModel)
}