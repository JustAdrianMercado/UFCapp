package com.ucb.app.fighters.presentation.state

sealed class FightersEffect {
    data class NavigateToFighterDetail(val fighterId: String) : FightersEffect()
}
