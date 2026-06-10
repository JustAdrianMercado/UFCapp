package com.ucb.app.fighters.presentation.state

sealed class FightersEvent {
    data object LoadFighters : FightersEvent()
    data class OnFighterClick(val fighterId: String) : FightersEvent()
    data class OnSearchQueryChanged(val query: String) : FightersEvent()
}
