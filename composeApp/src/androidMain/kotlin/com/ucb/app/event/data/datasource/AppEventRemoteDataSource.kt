package com.ucb.app.event.data.datasource

import com.ucb.app.portafolio.data.datasource.FirebaseManager

actual class AppEventRemoteDataSource actual constructor(
    private val firebaseManager: FirebaseManager
) {

    actual suspend fun saveEvent(path: String, value: String) {
        firebaseManager.saveData(path, value)
    }
}