package com.eighteen.eighteenandroid.domain.repository

import com.eighteen.eighteenandroid.domain.model.TournamentCategory

interface TournamentRepository {
    suspend fun getTournament(): Result<List<TournamentCategory>>

    suspend fun postTournament(
        accessToken: String,
        tournamentNo: Int,
        participantIdsOrderByRank: List<String>
    ): Result<String>
}