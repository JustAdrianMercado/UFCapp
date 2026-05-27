package com.ucb.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.app.config.presentation.screen.ConfigScreen
import com.ucb.app.country.presentation.screen.CountryScreen
import com.ucb.app.crypto.presentation.screen.CryptoScreen
import com.ucb.app.fakestore.presentation.screen.StoreScreen
import com.ucb.app.github.presentation.screen.GithubScreen
import com.ucb.app.movie.presentation.screen.MovieScreen
import com.ucb.app.portafolio.presentation.screen.PortafolioScreen
import com.ucb.app.fights.presentation.screen.FightListScreen
import com.ucb.app.ranking.presentation.screen.RankingScreen
import com.ucb.app.live.presentation.screen.LiveScreen
import com.ucb.app.fighters.presentation.screen.FightersScreen
import com.ucb.app.profile.presentation.screen.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Profile
    ) {
        composable<NavRoute.Profile> {
            ProfileScreen()
        }

        composable<NavRoute.ProfileEdit> {
        }

        composable<NavRoute.Github> {
            GithubScreen()
        }

        composable<NavRoute.Movies> {
            MovieScreen()
        }

        composable<NavRoute.Crypto> {
            CryptoScreen()
        }

        composable<NavRoute.FakeStore> {
            StoreScreen()
        }

        composable<NavRoute.CountryStore> {
            CountryScreen()
        }

        composable<NavRoute.Portafolio> {
            PortafolioScreen()
        }

        composable<NavRoute.Config>{
            ConfigScreen()
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
    }
}