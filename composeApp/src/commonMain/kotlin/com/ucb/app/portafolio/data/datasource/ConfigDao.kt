package com.ucb.app.portafolio.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: ConfigEntity)

    @Query("SELECT * FROM config WHERE `key` = :key LIMIT 1")
    fun observeConfig(key: String): Flow<ConfigEntity?>

    @Query("SELECT * FROM config WHERE `key` = :key LIMIT 1")
    suspend fun getConfig(key: String): ConfigEntity?
}