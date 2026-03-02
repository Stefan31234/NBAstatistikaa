package com.example.nbastatistika

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v1/games?per_page=20&seasons[]=2024")
    fun getGames(): Call<GameResponse>

    @GET("v1/games/{id}")
    fun getGameById(@Path("id") id: Int): Call<Game>

    @GET("v1/stats")
    fun getStatsByGame(@Query("game_ids[]") gameId: Int): Call<StatsResponse>

    @GET("v1/players")
    fun getPlayers(
        @Query("search") search: String?,
        @Query("per_page") perPage: Int = 25
    ): Call<PlayersResponse>

    @GET("v1/standings/grouped")
    fun getGroupedStandings(): Call<GroupedStandingsResponse>
}