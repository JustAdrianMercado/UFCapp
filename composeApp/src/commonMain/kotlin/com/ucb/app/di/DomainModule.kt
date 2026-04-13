package com.ucb.app.di

import com.ucb.app.crypto.domain.usecase.GetCryptosUseCase
import com.ucb.app.github.domain.usecase.GetAvatarUseCase
import com.ucb.app.movie.domain.usecase.GetMoviesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.ucb.app.portafolio.domain.usecase.SavePortafolioDataUseCase

val domainModule = module {
    singleOf(::GetAvatarUseCase)
    singleOf(::GetMoviesUseCase)
    singleOf(::GetCryptosUseCase)
    singleOf(::SavePortafolioDataUseCase)
}