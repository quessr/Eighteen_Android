package com.eighteen.eighteenandroid.presentation.myprofile.model

import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.School
import com.eighteen.eighteenandroid.domain.model.SnsLink

sealed interface MyProfileItem {
    data class Profile(
        val nickName: String,
        val profileUrl: String?,
        val school: School,
        val age: Int,
        val badgeCount: Int? = null,
        val teenDescription: String? = null
    ) : MyProfileItem

    data class Link(
        val links: List<SnsLink>
    ) : MyProfileItem

    data class Introduce(
        val mbti: String,
        val description: String?
    ) : MyProfileItem

    data class TenOfQna(
        val qnas: List<Qna>,
        val isExpanded: Boolean = false
    ) : MyProfileItem
}