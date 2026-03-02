package com.example.nbastatistika

data class StatsResponse(
    val data: List<StatLine>
)

data class StatLine(
    val id: Int,
    val game: StatGameRef,
    val team: StatTeamRef,
    val player: StatPlayerRef,
    val pts: Int,
    val reb: Int,
    val ast: Int,
    val stl: Int,
    val blk: Int,
    val turnover: Int,
    val fg3m: Int
)

data class StatGameRef(val id: Int)

data class StatTeamRef(
    val id: Int,
    val full_name: String,
    val abbreviation: String
)

data class StatPlayerRef(
    val id: Int,
    val first_name: String,
    val last_name: String
)