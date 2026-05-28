package com.ucb.app.onboarding.data.datasource

import java.util.Locale

actual class LanguageDataSource {
    actual fun getLanguage(): String {
        val language = Locale.getDefault().language

        return when (language) {
            "es" -> "es"
            "fr" -> "fr"
            else -> "en"
        }
    }
}