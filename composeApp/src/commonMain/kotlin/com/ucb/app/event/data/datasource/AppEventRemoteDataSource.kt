package com.ucb.app.event.data.datasource

import com.ucb.app.portafolio.data.datasource.FirebaseManager

expect class AppEventRemoteDataSource(firebaseManager: FirebaseManager) {
    suspend fun saveEvent(path: String, value: String)
}