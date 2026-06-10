package com.ucb.app.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class LogScheduler(
    private val context: Context
) {
    private val LOG_WORKNAME = "logUploadWork"
    private val LOG_ONE_TIME_WORKNAME = "abTestCheckNowWork"
    private val INTERVAL_MINUTES = 15L

    fun schedulePeriodicaUpload() {
        val logRequest = PeriodicWorkRequest.Builder(
            LogUploadWorker::class.java,
            INTERVAL_MINUTES,
            TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context.applicationContext)
            .enqueueUniquePeriodicWork(
                LOG_WORKNAME,
                ExistingPeriodicWorkPolicy.KEEP,
                logRequest
            )
    }

    fun scheduleImmediateAbTestCheck() {
        val request = OneTimeWorkRequestBuilder<LogUploadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context.applicationContext)
            .enqueueUniqueWork(
                LOG_ONE_TIME_WORKNAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
    }
}
