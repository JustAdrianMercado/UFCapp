package com.ucb.app.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class InitialConfigScheduler(
    private val context: Context
) {
    private val workName = "initialConfigWork"

    fun scheduleInitialConfigSync() {
        val request = OneTimeWorkRequestBuilder<InitialConfigWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context.applicationContext)
            .enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.KEEP,
                request
            )
    }
}