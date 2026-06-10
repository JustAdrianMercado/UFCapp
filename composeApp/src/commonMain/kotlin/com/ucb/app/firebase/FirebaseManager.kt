package com.ucb.app.firebase

expect class FirebaseManager() {
    suspend fun saveData(path: String, value: String)
    suspend fun getData(path: String): String?
}
