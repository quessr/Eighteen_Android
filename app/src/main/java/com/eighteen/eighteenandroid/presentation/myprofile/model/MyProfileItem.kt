package com.eighteen.eighteenandroid.presentation.myprofile.model

import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.model.SnsLink

sealed interface MyProfileItem {
    val id: String
    fun areItemsTheSame(other: MyProfileItem): Boolean
    fun areContentsTheSame(other: MyProfileItem): Boolean
    data class Profile(
        override val id: String,
        val nickName: String,
        val profileUrl: String?,
        val school: School,
        val age: Int,
        val badgeCount: Int? = null,
        val teenDescription: String? = null
    ) : MyProfileItem {
        override fun areItemsTheSame(other: MyProfileItem) =
            other is Profile && this.id == other.id

        override fun areContentsTheSame(other: MyProfileItem) =
            other is Profile && this == other
    }


    data class Link(
        override val id: String,
        val links: List<SnsLink>
    ) : MyProfileItem {
        override fun areItemsTheSame(other: MyProfileItem) =
            other is Link && this.id == other.id

        override fun areContentsTheSame(other: MyProfileItem) =
            other is Link && this == other
    }

    data class Introduce(
        override val id: String,
        val mbti: String,
        val description: String?
    ) : MyProfileItem {
        override fun areItemsTheSame(other: MyProfileItem) =
            other is Introduce && this.id == other.id

        override fun areContentsTheSame(other: MyProfileItem) =
            other is Introduce && this == other
    }

    data class TenOfQna(
        override val id: String,
        val qnas: List<Qna>,
        val isExpanded: Boolean = false
    ) : MyProfileItem {
        override fun areItemsTheSame(other: MyProfileItem) =
            other is TenOfQna && this.id == other.id && this.isExpanded == other.isExpanded

        override fun areContentsTheSame(other: MyProfileItem) =
            other is TenOfQna && this == other
    }
}