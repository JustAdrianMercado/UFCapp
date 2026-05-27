package com.ucb.app.di

import com.ucb.app.crypto.data.datasource.CryptoRemoteDataSource
import com.ucb.app.crypto.data.repository.CryptoRepositoryImpl
import com.ucb.app.crypto.data.service.CryptoService
import com.ucb.app.crypto.domain.repository.CryptoRepository
import com.ucb.app.github.data.datasource.GithubRemoteDataSource
import com.ucb.app.github.data.repository.GithubRepositoryImpl
import com.ucb.app.github.data.service.GitHubApiService
import com.ucb.app.github.domain.repository.GithubRepository
import com.ucb.app.movie.data.datasource.MovieRemoteDatasource
import com.ucb.app.movie.data.repository.MovieRepositoryImpl
import com.ucb.app.movie.data.service.MovieService
import com.ucb.app.movie.domain.repository.MovieRepository
import com.ucb.app.portafolio.data.datasource.FirebaseManager
import com.ucb.app.portafolio.data.repository.PortafolioRepositoryImpl
import com.ucb.app.portafolio.domain.repository.PortafolioRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.ucb.app.fights.data.repository.FightRepositoryImpl
import com.ucb.app.fights.domain.repository.FightRepository
import com.ucb.app.ranking.domain.repository.RankingRepository
import com.ucb.app.ranking.data.repository.RankingRepositoryImpl
import com.ucb.app.live.data.repository.LiveRepositoryImpl
import com.ucb.app.live.domain.repository.LiveRepository
import com.ucb.app.fighters.data.repository.FighterRepositoryImpl
import com.ucb.app.fighters.domain.repository.FighterRepository
import com.ucb.app.profile.data.repository.ProfileRepositoryImpl
import com.ucb.app.profile.domain.repository.ProfileRepository

val dataModule = module {
    singleOf(::GitHubApiService).bind<GithubRemoteDataSource>()
    singleOf(::GithubRepositoryImpl).bind<GithubRepository>()

    singleOf(::MovieService).bind<MovieRemoteDatasource>()
    singleOf(::MovieRepositoryImpl).bind<MovieRepository>()

    singleOf(::CryptoService).bind<CryptoRemoteDataSource>()
    singleOf(::CryptoRepositoryImpl).bind<CryptoRepository>()

    singleOf(::FirebaseManager)
    singleOf(::PortafolioRepositoryImpl).bind<PortafolioRepository>()

    singleOf(::FightRepositoryImpl).bind<FightRepository>()

    singleOf(::RankingRepositoryImpl).bind<RankingRepository>()
    singleOf(::LiveRepositoryImpl).bind<LiveRepository>()

    singleOf(::FighterRepositoryImpl).bind<FighterRepository>()

    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()
}