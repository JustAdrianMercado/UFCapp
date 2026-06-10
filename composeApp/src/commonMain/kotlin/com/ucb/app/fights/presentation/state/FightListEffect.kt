package com.ucb.app.fights.presentation.state

sealed class FightListEffect {
    data class NavigateToFightDetail(val fightId: String) : FightListEffect()
}
