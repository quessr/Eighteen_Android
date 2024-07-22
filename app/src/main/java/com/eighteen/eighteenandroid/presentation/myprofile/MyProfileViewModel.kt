package com.eighteen.eighteenandroid.presentation.myprofile

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.Profile
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyProfileViewModel : ViewModel() {
    private val _myProfileItemsStateFlow = MutableStateFlow<List<MyProfileItem>>(emptyList())
    val myProfileItemsStateFlow = _myProfileItemsStateFlow.asStateFlow()

    fun setProfile(profile: Profile) {
        _myProfileItemsStateFlow.value = asProfileItems(profile = profile)
    }

    fun setQnaExpanded(isExpanded: Boolean) {
        _myProfileItemsStateFlow.value = myProfileItemsStateFlow.value.toMutableList().apply {
            replaceAll {
                if (it is MyProfileItem.TenOfQna) it.copy(isExpanded = isExpanded) else it
            }
        }
    }

    private fun asProfileItems(profile: Profile): List<MyProfileItem> {
        with(profile) {
            val profileItem = MyProfileItem.Profile(
                id = id,
                nickName = nickName,
                age = age,
                profileUrl = medias.firstOrNull()?.url,
                school = school
            )
            val linkItem = MyProfileItem.Link(id = id, links = snsLinks)
            val introduceItem =
                MyProfileItem.Introduce(id = id, mbti = mbti, description = description)
            val qnaItem = MyProfileItem.TenOfQna(id = id, qnas = qna)
            return listOf(profileItem, linkItem, introduceItem, qnaItem)
        }
    }
}