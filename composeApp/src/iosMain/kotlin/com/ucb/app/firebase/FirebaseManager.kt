package com.ucb.app.firebase

actual class FirebaseManager actual constructor() {
    actual suspend fun saveData(path: String, value: String) {
        println("Firebase iOS: saveData aún no implementado")
    }

    actual suspend fun getData(path: String): String? {
        println("Firebase iOS: getData aún no implementado")
        return null
    }
}
