package com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.eighteen.eighteenandroid.domain.model.Tournament

class TournamentDiffCallBack: DiffUtil.ItemCallback<Tournament>() {
    override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem == newItem
    }
}