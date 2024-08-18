package com.eighteen.eighteenandroid.presentation.profiledetail.model

sealed class ProfileDetailModel {
    abstract val id: String

    data class ProfileInfo(
        override val id: String,
        val name: String,
        val age: Int,
        val school: String,
    ) : ProfileDetailModel()

    data class MediaItem(
        val url: String?,
        val isVideo: Boolean
    )

    data class ProfileImages(
        override val id: String,
        val mediaItems: List<MediaItem>,
        var currentPosition: Int = 0,
        val likeCount: Int,
        val isLiked: Boolean = false
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

    data class QnaListTitle(
        override val id: String
    ) : ProfileDetailModel()

    data class QnaToggle(
        override val id: String,
        val isExpanded: Boolean
    ) : ProfileDetailModel()

}
