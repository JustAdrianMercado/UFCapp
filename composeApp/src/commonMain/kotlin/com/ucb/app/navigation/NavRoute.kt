package com.ucb.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class
NavRoute {

    @Serializable
    object Profile : NavRoute()

    @Serializable
    object ProfileEdit : NavRoute()

    @Serializable
    object Fights : NavRoute()
    @Serializable
    object Ranking : NavRoute()
    @Serializable
    object Live : NavRoute()
    @Serializable
    object Fighters : NavRoute()
    @Serializable
    object Login : NavRoute()

    @Serializable
    object ForgotPassword : NavRoute()

    @Serializable
    object VerifyCode : NavRoute()

    @Serializable
    object ResetPassword : NavRoute()
    @Serializable
    object Onboarding : NavRoute()
}

