package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.repository.TournamentRepository
import javax.inject.Inject

class SubmitTournamentResultsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend operator fun invoke(
        accessToken: String, tournamentNo: Int,
        participantIdsOrderByRank: List<String>
    ) = repository.postTournament(
        accessToken = accessToken,
        tournamentNo = tournamentNo,
        participantIdsOrderByRank = participantIdsOrderByRank
    )
}