package com.ucb.app.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ucb.app.event.domain.usecase.RegisterAppEventUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AppLifecycleObserver(
    private val registerAppEventUseCase: RegisterAppEventUseCase
) : DefaultLifecycleObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStart(owner: LifecycleOwner) {
        scope.launch {
            registerAppEventUseCase("OPEN")
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        scope.launch {
            registerAppEventUseCase("CLOSE")
        }
    }
}