package com.ucb.app.fights.presentation.state

sealed class FightListEvent {
    data object LoadFights : FightListEvent()
    data class OnFightClick(val fightId: String) : FightListEvent()
}
