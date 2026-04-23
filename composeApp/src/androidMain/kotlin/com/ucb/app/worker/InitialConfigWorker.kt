package com.ucb.app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ucb.app.config.domain.repository.ConfigRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InitialConfigWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val configRepository: ConfigRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            configRepository.syncInitialConfig("welcome_message")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}