package com.ucb.app.event.data.datasource

import com.ucb.app.firebase.FirebaseManager

actual class AppEventRemoteDataSource actual constructor(firebaseManager: FirebaseManager) {
    actual suspend fun saveEvent(path: String, value: String) {
    }
}
