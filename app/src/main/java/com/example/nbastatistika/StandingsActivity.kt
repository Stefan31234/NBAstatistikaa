package com.example.nbastatistika

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingsActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standings)

        rv = findViewById(R.id.rvStandings)
        rv.layoutManager = LinearLayoutManager(this)

        loadStandings()
    }

    private fun loadStandings() {
        RetrofitClient.api.getGroupedStandings().enqueue(object : Callback<GroupedStandingsResponse> {
            override fun onResponse(
                call: Call<GroupedStandingsResponse>,
                response: Response<GroupedStandingsResponse>
            ) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(this@StandingsActivity, "API error: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }

                val body = response.body()!!
                val rows = mutableListOf<StandingsAdapter.RowItem>()

                for (g in body.groups) {
                    rows.add(StandingsAdapter.RowItem.Header(g.title))
                    for (t in g.teams) {
                        rows.add(StandingsAdapter.RowItem.TeamRow(t))
                    }
                }

                rv.adapter = StandingsAdapter(rows)
            }

            override fun onFailure(call: Call<GroupedStandingsResponse>, t: Throwable) {
                Toast.makeText(this@StandingsActivity, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}