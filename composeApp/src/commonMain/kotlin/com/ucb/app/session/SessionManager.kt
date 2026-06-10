package com.ucb.app.session

class SessionManager {
    var currentUserId: String? = null
        private set

    fun startSession(userId: String) {
        currentUserId = userId
    }

    fun updateSessionUserId(userId: String) {
        currentUserId = userId
    }

    fun clearSession() {
        currentUserId = null
    }
}
