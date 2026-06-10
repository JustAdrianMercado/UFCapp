package com.ucb.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.app.fights.presentation.screen.FightListScreen
import com.ucb.app.ranking.presentation.screen.RankingScreen
import com.ucb.app.live.presentation.screen.LiveScreen
import com.ucb.app.fighters.presentation.screen.FightersScreen
import com.ucb.app.profile.presentation.screen.ProfileEditScreen
import com.ucb.app.profile.presentation.screen.ProfileScreen
import com.ucb.app.auth.presentation.screen.LoginScreen
import com.ucb.app.auth.presentation.screen.ForgotPasswordScreen
import com.ucb.app.auth.presentation.screen.VerifyCodeScreen
import com.ucb.app.auth.presentation.screen.ResetPasswordScreen
import com.ucb.app.fights.presentation.screen.HomeScreen
import com.ucb.app.onboarding.presentation.screen.OnboardingScreen
import com.ucb.app.session.SessionManager
import org.koin.compose.koinInject

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val sessionManager: SessionManager = koinInject()
    val navigateToProfileAfterLogin = remember { mutableStateOf(false) }

    fun navigateFromStar() {
        if (sessionManager.currentUserId == null) {
            navigateToProfileAfterLogin.value = true
            navController.navigate(NavRoute.Login)
        } else {
            navController.navigate(NavRoute.Profile)
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Onboarding
    ) {
        composable<NavRoute.Profile> {
            ProfileScreen(
                onNavigateToHome = { navController.navigate(NavRoute.Home) },
                onNavigateToLive = { navController.navigate(NavRoute.Live) },
                onNavigateToRanking = { navController.navigate(NavRoute.Ranking) },
                onNavigateToFighters = { navController.navigate(NavRoute.Fighters) },
                onNavigateToProfile = { /* Already here */ },
                onNavigateBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(NavRoute.ProfileEdit) },
                onNavigateToLogin = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.Profile) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.ProfileEdit> {
            ProfileEditScreen(
                onNavigateBack = { navController.popBackStack() },
                onChangePassword = { navController.navigate(NavRoute.ResetPassword) }
            )
        }

        composable<NavRoute.Fights> {
            FightListScreen(
                onNavigateToHome = { navController.navigate(NavRoute.Home) },
                onNavigateToLive = { navController.navigate(NavRoute.Live) },
                onNavigateToRanking = { navController.navigate(NavRoute.Ranking) },
                onNavigateToFighters = { navController.navigate(NavRoute.Fighters) },
                onNavigateToProfile = { navigateFromStar() },
                onSearchClick = { /* Already here or just reload */ }
            )
        }

        composable<NavRoute.Home> {
            HomeScreen(
                onViewAllFights = { navController.navigate(NavRoute.Fights) },
                onNavigateToLive = { navController.navigate(NavRoute.Live) },
                onNavigateToRanking = { navController.navigate(NavRoute.Ranking) },
                onNavigateToFighters = { navController.navigate(NavRoute.Fighters) },
                onNavigateToProfile = { navigateFromStar() },
                onSearchClick = { navController.navigate(NavRoute.Fights) }
            )
        }

        composable<NavRoute.Ranking> {
            RankingScreen(
                onNavigateToHome = { navController.navigate(NavRoute.Home) },
                onNavigateToLive = { navController.navigate(NavRoute.Live) },
                onNavigateToFighters = { navController.navigate(NavRoute.Fighters) },
                onNavigateToProfile = { navigateFromStar() },
                onSearchClick = { navController.navigate(NavRoute.Fights) }
            )
        }

        composable<NavRoute.Live> {
            LiveScreen(
                onNavigateToHome = { navController.navigate(NavRoute.Home) },
                onNavigateToRanking = { navController.navigate(NavRoute.Ranking) },
                onNavigateToFighters = { navController.navigate(NavRoute.Fighters) },
                onNavigateToProfile = { navigateFromStar() },
                onSearchClick = { navController.navigate(NavRoute.Fights) }
            )
        }

        composable<NavRoute.Fighters> {
            FightersScreen(
                onNavigateToHome = { navController.navigate(NavRoute.Home) },
                onNavigateToLive = { navController.navigate(NavRoute.Live) },
                onNavigateToRanking = { navController.navigate(NavRoute.Ranking) },
                onNavigateToProfile = { navigateFromStar() },
                onSearchClick = { navController.navigate(NavRoute.Fights) }
            )
        }

        composable<NavRoute.Login> {
            LoginScreen(
                onNavigateHome = {
                    val destination = if (navigateToProfileAfterLogin.value) {
                        NavRoute.Profile
                    } else {
                        NavRoute.Home
                    }
                    navigateToProfileAfterLogin.value = false
                    navController.navigate(destination) {
                        popUpTo(NavRoute.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavRoute.ForgotPassword> {
            ForgotPasswordScreen()
        }

        composable<NavRoute.VerifyCode> {
            VerifyCodeScreen()
        }

        composable<NavRoute.ResetPassword> {
            ResetPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable<NavRoute.Onboarding> {
            OnboardingScreen {
                navController.navigate(NavRoute.Home) {
                    popUpTo(NavRoute.Onboarding) {
                        inclusive = true
                    }
                }
            }
        }
    }
}
