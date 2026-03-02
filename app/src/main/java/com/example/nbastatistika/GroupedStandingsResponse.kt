package com.example.nbastatistika

data class GroupedStandingsResponse(
    val groups: List<StandingGroup>
)

data class StandingGroup(
    val title: String,
    val teams: List<StandingTeam>
)

data class StandingTeam(
    val id: Int,
    val full_name: String,
    val abbr: String,
    val wins: Int,
    val losses: Int,
    val pct: Double
)