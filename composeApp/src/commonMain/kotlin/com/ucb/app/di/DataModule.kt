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
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.ucb.app.portafolio.data.datasource.FirebaseManager
import com.ucb.app.portafolio.data.repository.PortafolioRepositoryImpl
import com.ucb.app.portafolio.domain.repository.PortafolioRepository

val dataModule = module {
    singleOf(::GitHubApiService).bind<GithubRemoteDataSource>()
    singleOf(::GithubRepositoryImpl).bind<GithubRepository>()
    singleOf(::MovieRepositoryImpl).bind<MovieRepository>()
    singleOf(::MovieService).bind<MovieRemoteDatasource>()

    singleOf(::CryptoService).bind<CryptoRemoteDataSource>()
    singleOf(::CryptoRepositoryImpl).bind<CryptoRepository>()

    singleOf(::FirebaseManager)
    singleOf(::PortafolioRepositoryImpl).bind<PortafolioRepository>()
}