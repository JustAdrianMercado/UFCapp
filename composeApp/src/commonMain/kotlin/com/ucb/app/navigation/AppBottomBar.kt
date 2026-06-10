package com.ucb.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.nav_fighters
import kotlinproject.composeapp.generated.resources.nav_home
import kotlinproject.composeapp.generated.resources.nav_live
import kotlinproject.composeapp.generated.resources.nav_rankings
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBottomBar(
    currentRoute: NavRoute,
    onNavigateToHome: () -> Unit,
    onNavigateToLive: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToFighters: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFB11212)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(stringResource(Res.string.nav_home), currentRoute is NavRoute.Home, onNavigateToHome)
            BottomNavItem(stringResource(Res.string.nav_live), currentRoute is NavRoute.Live, onNavigateToLive)
            BottomNavItem(stringResource(Res.string.nav_rankings), currentRoute is NavRoute.Ranking, onNavigateToRanking)
            BottomNavItem(stringResource(Res.string.nav_fighters), currentRoute is NavRoute.Fighters, onNavigateToFighters)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateToProfile() }
            )
        }
    }
}

@Composable
private fun BottomNavItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
        color = if (selected) Color.White else Color(0xFFE0E0E0),
        fontSize = 14.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() }
    )
}
