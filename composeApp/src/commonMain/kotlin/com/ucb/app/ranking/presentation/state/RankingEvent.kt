package com.ucb.app.ranking.presentation.state

sealed class RankingEvent {
    data object LoadRankings : RankingEvent()
    data class OnFighterClick(val fighterId: String) : RankingEvent()
}
