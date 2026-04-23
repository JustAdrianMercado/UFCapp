package com.ucb.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.ucb.app.event.domain.usecase.RegisterAppEventUseCase
import com.ucb.app.worker.InitialConfigScheduler
import com.ucb.app.worker.LogScheduler
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val registerAppEventUseCase: RegisterAppEventUseCase by inject()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("FCM", "Notification permission granted: $isGranted")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogScheduler(this).schedulePeriodicaUpload()
        InitialConfigScheduler(this).scheduleInitialConfigSync()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FCM", "Token: $token")
            }

        setContent {
            App()
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            registerAppEventUseCase("OPEN")
        }
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            registerAppEventUseCase("CLOSE")
        }
    }
}