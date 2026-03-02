package com.example.nbastatistika

data class Game(
    val id: Int,
    val home_team: Team,
    val visitor_team: Team,
    val home_team_score: Int,
    val visitor_team_score: Int,
    val status: String,
    val date: String,
    val datetime: String?
)

