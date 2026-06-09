package com.ucb.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.app.fights.presentation.screen.FightListScreen
import com.ucb.app.ranking.presentation.screen.RankingScreen
import com.ucb.app.live.presentation.screen.LiveScreen
import com.ucb.app.fighters.presentation.screen.FightersScreen
import com.ucb.app.profile.presentation.screen.ProfileScreen
import com.ucb.app.auth.presentation.screen.LoginScreen
import com.ucb.app.auth.presentation.screen.ForgotPasswordScreen
import com.ucb.app.auth.presentation.screen.VerifyCodeScreen
import com.ucb.app.auth.presentation.screen.ResetPasswordScreen
import com.ucb.app.onboarding.presentation.screen.OnboardingScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Onboarding
    ) {
        composable<NavRoute.Profile> {
            ProfileScreen()
        }

        composable<NavRoute.ProfileEdit> {
        }

        composable<NavRoute.Fights> {
            FightListScreen()
        }

        composable<NavRoute.Ranking> {
            RankingScreen()
        }

        composable<NavRoute.Live> {
            LiveScreen()
        }

        composable<NavRoute.Fighters> {
            FightersScreen()
        }

        composable<NavRoute.Login> {
            LoginScreen()
        }

        composable<NavRoute.ForgotPassword> {
            ForgotPasswordScreen()
        }

        composable<NavRoute.VerifyCode> {
            VerifyCodeScreen()
        }

        composable<NavRoute.ResetPassword> {
            ResetPasswordScreen()
        }

        composable<NavRoute.Onboarding> {
            OnboardingScreen(
                onNavigateHome = {
                    navController.navigate(NavRoute.Fights) {
                        popUpTo(NavRoute.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}