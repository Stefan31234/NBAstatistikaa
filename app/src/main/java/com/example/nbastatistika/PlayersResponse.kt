package com.example.nbastatistika

data class PlayersResponse(
    val data: List<PlayerDto>
)

data class PlayerDto(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val team: PlayerTeamDto
)

data class PlayerTeamDto(
    val id: Int,
    val full_name: String,
    val abbreviation: String
)