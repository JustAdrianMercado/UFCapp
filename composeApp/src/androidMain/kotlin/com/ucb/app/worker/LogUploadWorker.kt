package com.ucb.app.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ucb.app.firebase.FirebaseManager
import com.ucb.app.notification.LocalNotificationHelper
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LogUploadWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun doWork(): Result {
        return try {
            val variant = fetchVariant()
            val message = messageForVariant(variant)
            val executedAt = System.currentTimeMillis()
            val result = AbTestResult(
                variant = variant,
                message = message,
                executedAt = executedAt
            )

            FirebaseManager().saveData(
                path = "ab_tests/$executedAt",
                value = json.encodeToString(result)
            )
            LocalNotificationHelper(applicationContext).showAbTestNotification(variant, message)

            Log.d(TAG, "A/B test executed. Variant=$variant")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "A/B test worker failed", e)
            Result.retry()
        }
    }

    private suspend fun fetchVariant(): String {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.fetchAndActivate().await()
        val remoteVariant = remoteConfig.getString(REMOTE_CONFIG_KEY).trim().uppercase()
        return if (remoteVariant == VARIANT_B) VARIANT_B else VARIANT_A
    }

    private fun messageForVariant(variant: String): String {
        return when (variant) {
            VARIANT_B -> "Don't miss tonight's UFC card. Open CageX for the latest fights."
            else -> "Upcoming UFC fights synced. Check CageX for fresh fight updates."
        }
    }

    private companion object {
        const val TAG = "LogUploadWorker"
        const val REMOTE_CONFIG_KEY = "ab_notification_variant"
        const val VARIANT_A = "A"
        const val VARIANT_B = "B"
    }
}

@Serializable
private data class AbTestResult(
    val variant: String,
    val message: String,
    val executedAt: Long
)
