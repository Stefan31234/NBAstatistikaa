package com.example.nbastatistika

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailsActivity : AppCompatActivity() {

    private lateinit var tvTeams: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvStatus: TextView
    private lateinit var rvStats: RecyclerView

    private val statRows = mutableListOf<StatLine>()
    private lateinit var statsAdapter: StatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)

        tvTeams = findViewById(R.id.tvDetailTeams)
        tvScore = findViewById(R.id.tvDetailScore)
        tvStatus = findViewById(R.id.tvDetailStatus)
        rvStats = findViewById(R.id.recyclerStats)

        rvStats.layoutManager = LinearLayoutManager(this)
        statsAdapter = StatsAdapter(statRows)
        rvStats.adapter = statsAdapter

        val gameId = intent.getIntExtra("game_id", -1)
        if (gameId == -1) {
            Toast.makeText(this, "Missing game_id", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        loadGame(gameId)
        loadStats(gameId)
    }

    private fun loadGame(gameId: Int) {
        RetrofitClient.api.getGameById(gameId).enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (!response.isSuccessful || response.body() == null) return
                val g = response.body()!!

                tvTeams.text = "${g.visitor_team.full_name} vs ${g.home_team.full_name}"
                tvStatus.text = g.status
                tvScore.text = "${g.visitor_team_score} - ${g.home_team_score}"
            }

            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(this@MatchDetailsActivity, "Game error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadStats(gameId: Int) {
        RetrofitClient.api.getStatsByGame(gameId).enqueue(object : Callback<StatsResponse> {
            override fun onResponse(call: Call<StatsResponse>, response: Response<StatsResponse>) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(this@MatchDetailsActivity, "Stats API error: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }

                statRows.clear()
                statRows.addAll(response.body()!!.data)
                statsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<StatsResponse>, t: Throwable) {
                Toast.makeText(this@MatchDetailsActivity, "Stats error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}