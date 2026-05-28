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
    object Github : NavRoute()

    @Serializable
    object Movies : NavRoute()

    @Serializable
    object Crypto : NavRoute()

    @Serializable
    object FakeStore : NavRoute()

    @Serializable
    object CountryStore : NavRoute()

    @Serializable
    object Portafolio : NavRoute()
    @Serializable
    object Config : NavRoute()
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

