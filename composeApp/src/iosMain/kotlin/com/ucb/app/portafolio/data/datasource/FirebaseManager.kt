package com.ucb.app.portafolio.data.datasource

actual class FirebaseManager actual constructor() {
    actual suspend fun saveData(path: String, value: String) {
        println("Firebase iOS: saveData aún no implementado")
    }
}