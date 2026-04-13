package com.ucb.app.crypto.data.repository

import com.ucb.app.crypto.data.datasource.CryptoRemoteDataSource
import com.ucb.app.crypto.data.mapper.toModel
import com.ucb.app.crypto.domain.model.CryptoModel
import com.ucb.app.crypto.domain.repository.CryptoRepository

class CryptoRepositoryImpl(
    private val remoteDataSource: CryptoRemoteDataSource
) : CryptoRepository {

    override suspend fun getCryptos(): List<CryptoModel> {
        return remoteDataSource
            .getCryptos()
            .map { it.toModel() }
    }
}