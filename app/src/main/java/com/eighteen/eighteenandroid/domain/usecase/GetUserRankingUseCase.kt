package com.eighteen.eighteenandroid.domain.usecase

import com.eighteen.eighteenandroid.domain.model.UserRankInfo
import com.eighteen.eighteenandroid.domain.repository.TournamentRepository
import javax.inject.Inject

class GetUserRankingUseCase  @Inject constructor(private val repository: TournamentRepository){
    suspend operator fun invoke(tournamentId: Int): Result<List<UserRankInfo>> =
        repository.getTournamentResult(tournamentId)
}