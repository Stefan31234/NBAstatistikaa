package com.example.nbastatistika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchAdapter(
    private val matches: List<Match>,
    private val onItemClick: (Match) -> Unit
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teams: TextView = itemView.findViewById(R.id.tvTeams)
        val score: TextView = itemView.findViewById(R.id.tvScore)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.teams.text = match.teams
        holder.score.text = match.mainLine
        holder.status.text = match.status

        holder.itemView.setOnClickListener {
            onItemClick(match)
        }
    }

    override fun getItemCount(): Int = matches.size
}