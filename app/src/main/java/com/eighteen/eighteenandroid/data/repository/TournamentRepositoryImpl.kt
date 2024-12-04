package com.eighteen.eighteenandroid.data.repository

import com.eighteen.eighteenandroid.data.datasource.remote.request.TournamentRequest
import com.eighteen.eighteenandroid.data.datasource.remote.service.TournamentService
import com.eighteen.eighteenandroid.data.mapper.TournamentMapper
import com.eighteen.eighteenandroid.data.mapper.mapper
import com.eighteen.eighteenandroid.domain.model.TournamentCategory
import com.eighteen.eighteenandroid.domain.repository.TournamentRepository
import javax.inject.Inject

class TournamentRepositoryImpl @Inject constructor(private val tournamentService: TournamentService) :
    TournamentRepository {
    override suspend fun getTournament(): Result<List<TournamentCategory>> = runCatching {
        tournamentService.getTournamentParticipants().mapper { response ->
            response.data?.map { tournamentResponse ->
                TournamentMapper.asTournamentCategoryModel(
                    tournamentCategoryResponse = tournamentResponse
                )
            } ?: emptyList()
        }
    }

    override suspend fun postTournament(
        accessToken: String,
        tournamentNo: Int,
        participantIdsOrderByRank: List<String>
    ) = runCatching {
        val request = TournamentRequest(
            tournamentNo = tournamentNo,
            participantIdsOrderByRank = participantIdsOrderByRank
        )

        tournamentService.submitTournamentResults(tournamentRequest = request).mapper {
            it.data ?: ""
        }
    }
}