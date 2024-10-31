package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.TournamentVoteResultResponse
import com.eighteen.eighteenandroid.domain.model.UserRankInfo

object TournamentMapper {
    fun asUserRankInfoList(response: List<TournamentVoteResultResponse>): List<UserRankInfo> {
        val sumOfVoteCount = response.sumOf { it.voteCount }

        return response.map {
            UserRankInfo(
                rankerId = it.rankerId,
                rankerNickName = it.rankerNickName,
                rank = it.rank,
                profileImageUrl = it.profileImageUrl,
                voteRate = (it.voteCount / sumOfVoteCount.toDouble()) * 100
            )
        }
    }
}