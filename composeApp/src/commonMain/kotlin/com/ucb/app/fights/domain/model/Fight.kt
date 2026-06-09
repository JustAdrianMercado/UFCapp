package com.ucb.app.fights.domain.model

data class Fight(
    val id: String,
    val fighter1: String,
    val fighter2: String,
    val eventName: String,
    val date: String,
    val imageUrl: String,
    val isMain: Boolean = false
)