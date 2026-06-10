package com.ucb.app.event.data.datasource

import com.ucb.app.firebase.FirebaseManager

expect class AppEventRemoteDataSource(firebaseManager: FirebaseManager) {
    suspend fun saveEvent(path: String, value: String)
}
