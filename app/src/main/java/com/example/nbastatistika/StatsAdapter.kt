package com.example.nbastatistika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatsAdapter(private val stats: List<StatLine>) : RecyclerView.Adapter<StatsAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlayer: TextView = itemView.findViewById(R.id.tvPlayer)
        val tvLine: TextView = itemView.findViewById(R.id.tvLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = stats[position]
        holder.tvPlayer.text = "${s.player.first_name} ${s.player.last_name} (${s.team.abbreviation})"
        holder.tvLine.text = "PTS ${s.pts}  REB ${s.reb}  AST ${s.ast}  STL ${s.stl}  BLK ${s.blk}  3PM ${s.fg3m}  TOV ${s.turnover}"
    }

    override fun getItemCount(): Int = stats.size
}