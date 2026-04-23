package com.ucb.app.di

import androidx.room.Room
import com.ucb.app.config.data.datasource.ConfigLocalDataSource
import com.ucb.app.config.data.datasource.RemoteConfigDataSource
import com.ucb.app.config.data.repository.ConfigRepositoryImpl
import com.ucb.app.config.domain.repository.ConfigRepository
import com.ucb.app.portafolio.data.datasource.AppDatabase
import org.koin.dsl.module

val androidConfigModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().configDao() }

    single { ConfigLocalDataSource(get()) }
    single { RemoteConfigDataSource() }

    single<ConfigRepository> {
        ConfigRepositoryImpl(
            remoteConfigDataSource = get(),
            localDataSource = get()
        )
    }
}