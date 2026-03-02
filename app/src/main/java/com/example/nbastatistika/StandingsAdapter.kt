package com.example.nbastatistika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StandingsAdapter(
    private val items: List<RowItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_TEAM = 1
    }

    sealed class RowItem {
        data class Header(val title: String) : RowItem()
        data class TeamRow(val team: StandingTeam) : RowItem()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is RowItem.Header -> TYPE_HEADER
            is RowItem.TeamRow -> TYPE_TEAM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            val v = inflater.inflate(R.layout.item_group_header, parent, false)
            HeaderVH(v)
        } else {
            val v = inflater.inflate(R.layout.item_standing_team, parent, false)
            TeamVH(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is RowItem.Header -> (holder as HeaderVH).bind(item)
            is RowItem.TeamRow -> (holder as TeamVH).bind(item.team)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv: TextView = itemView.findViewById(R.id.tvGroupHeader)
        fun bind(item: RowItem.Header) {
            tv.text = item.title
            tv.textSize = 16f
        }
    }

    class TeamVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTeam: TextView = itemView.findViewById(R.id.tvTeamName)
        private val tvW: TextView = itemView.findViewById(R.id.tvW)
        private val tvL: TextView = itemView.findViewById(R.id.tvL)
        private val tvPct: TextView = itemView.findViewById(R.id.tvPct)

        fun bind(t: StandingTeam) {
            tvTeam.text = "${t.abbr} - ${t.full_name}"
            tvW.text = "W ${t.wins}"
            tvL.text = "L ${t.losses}"
            tvPct.text = String.format("%.3f", t.pct)
        }
    }
}