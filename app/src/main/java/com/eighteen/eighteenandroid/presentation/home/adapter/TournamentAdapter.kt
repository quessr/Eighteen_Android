package com.eighteen.eighteenandroid.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemTournamentBinding
import com.eighteen.eighteenandroid.domain.model.Tournament
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.TournamentDiffCallBack

class TournamentAdapter(
    private val listener: MainAdapterListener
): ListAdapter<Tournament, TournamentAdapter.TournamentViewHolder>(TournamentDiffCallBack()) {

    class TournamentViewHolder(
        private val binding: ItemTournamentBinding,
        private val listener: MainAdapterListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(tournament: Tournament) {
            with(binding) {
                when(tournament) {
                    is Tournament.Exercise -> {
                        ivTournament.setImageResource(R.drawable.bg_tournament_exercise)
                    }
                    is Tournament.Study -> {
                        ivTournament.setImageResource(R.drawable.bg_tournament_study)
                    }
                }

                root.setOnClickListener {
                    listener.onTournamentClicks(tournament)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemTournamentBinding.inflate(layoutInflater, parent, false)

        return TournamentViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}