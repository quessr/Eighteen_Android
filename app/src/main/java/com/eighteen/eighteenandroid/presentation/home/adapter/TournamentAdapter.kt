package com.eighteen.eighteenandroid.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemTournamentBinding
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.TournamentDiffCallBack

class TournamentAdapter: ListAdapter<Tournament, TournamentAdapter.TournamentViewHolder>(TournamentDiffCallBack()) {

    class TournamentViewHolder(private val binding: ItemTournamentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Tournament) {
            with(binding) {
                when(item) {
                    is Tournament.Exercise -> {
                        ivTournament.setImageResource(R.drawable.bg_tournament_exercise)
                    }
                    is Tournament.Study -> {
                        ivTournament.setImageResource(R.drawable.bg_tournament_study)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemTournamentBinding.inflate(layoutInflater, parent, false)

        return TournamentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}