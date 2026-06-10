package com.ucb.app.ranking.presentation.state

sealed class RankingEffect {
    data class NavigateToFighterDetail(val fighterId: String) : RankingEffect()
}
