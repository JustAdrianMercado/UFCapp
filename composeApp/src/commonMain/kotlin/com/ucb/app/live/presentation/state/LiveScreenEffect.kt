package com.ucb.app.live.presentation.state

sealed class LiveScreenEffect {
    data class NavigateToEventDetail(val eventId: String) : LiveScreenEffect()
    data object OpenParamountExternal : LiveScreenEffect()
}
