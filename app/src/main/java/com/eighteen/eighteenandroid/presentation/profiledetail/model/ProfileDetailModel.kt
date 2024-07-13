package com.eighteen.eighteenandroid.presentation.profiledetail.model

sealed class ProfileDetailModel {
    abstract val id: String

    data class ProfileInfo(
        override val id: String,
        val name: String,
        val age: Int,
        val school: String,
    ) : ProfileDetailModel()

    data class ProfileImages(
        override val id: String,
        val imageUrl: List<String>
    ) : ProfileDetailModel()

    data class Like(
        override val id: String,
        val likeCount: Int
    ) : ProfileDetailModel()

    data class BadgeAndTeen(
        override val id: String,
        val badgeCount: Int,
        val teenAward: String
    ) : ProfileDetailModel()

    data class Introduction(
        override val id: String,
        val personalityType: String,
        val introductionText: String
    ) : ProfileDetailModel()

    data class Qna(
        override val id: String,
        val question: String,
        val answer: String
    ) : ProfileDetailModel()

    data class QnaList(
        override val id: String,
        val qnas: List<Qna>
    ) : ProfileDetailModel()
}
