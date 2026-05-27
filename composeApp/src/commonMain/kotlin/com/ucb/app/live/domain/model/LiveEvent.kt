package com.ucb.app.live.domain.model

data class LiveEvent(
    val id: String,
    val platform: String,
    val title: String,
    val date: String,
    val time: String,
    val accessType: String
)