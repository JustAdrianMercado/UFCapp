package com.ucb.app.live.presentation.state

sealed class LiveScreenEvent {
    data object LoadLiveEvents : LiveScreenEvent()
    data class OnEventClick(val eventId: String) : LiveScreenEvent()
    data object OnParamountClick : LiveScreenEvent()
}
