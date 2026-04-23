package com.ucb.app.di

import com.ucb.app.event.data.datasource.AppEventLocalDataSource
import com.ucb.app.event.data.datasource.AppEventRemoteDataSource
import com.ucb.app.event.data.repository.AppEventRepositoryImpl
import com.ucb.app.event.domain.repository.AppEventRepository
import com.ucb.app.portafolio.data.datasource.AppDatabase
import org.koin.dsl.module

val androidEventModule = module {

    single { get<AppDatabase>().appEventDao() }

    single { AppEventLocalDataSource(get()) }
    single { AppEventRemoteDataSource(get()) }

    single<AppEventRepository> {
        AppEventRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}