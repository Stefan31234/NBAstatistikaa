package com.example.nbastatistika

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var matchAdapter: MatchAdapter
    private val matchList = mutableListOf<Match>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        matchAdapter = MatchAdapter(matchList) { match ->
            val intent = android.content.Intent(this, MatchDetailsActivity::class.java)
            intent.putExtra("game_id", match.gameId)
            startActivity(intent)
        }
        recyclerView.adapter = matchAdapter

        findViewById<Button>(R.id.btnStandings).setOnClickListener {
            startActivity(Intent(this, StandingsActivity::class.java))
        }

        loadGames()
    }

    private fun loadGames() {
        RetrofitClient.api.getGames().enqueue(object : Callback<GameResponse> {

            override fun onResponse(
                call: Call<GameResponse>,
                response: Response<GameResponse>
            ) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(
                        this@MainActivity,
                        "API error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                matchList.clear()

                for (game in response.body()!!.data) {

                    val home = game.home_team.full_name
                    val away = game.visitor_team.full_name

                    val hs = game.home_team_score
                    val vs = game.visitor_team_score

                    // ===== VREME MEČA =====
                    val startTime = try {
                        if (game.datetime != null) {
                            val instant = Instant.parse(game.datetime)
                            DateTimeFormatter.ofPattern("HH:mm")
                                .withZone(ZoneId.systemDefault())
                                .format(instant)
                        } else {
                            "TBD"
                        }
                    } catch (e: Exception) {
                        "TBD"
                    }

                    // ===== GLAVNA LINIJA =====
                    val isFinal = game.status.equals("Final", ignoreCase = true)

                    val mainLine = when {
                        hs == 0 && vs == 0 && isFinal -> "N/A"
                        hs == 0 && vs == 0 -> startTime
                        else -> "$hs - $vs"
                    }

                    matchList.add(
                        Match(
                            gameId = game.id,
                            teams = "$away vs $home",
                            mainLine = mainLine,
                            status = game.status
                        )
                    )
                }

                matchAdapter.notifyDataSetChanged()
            }


            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
