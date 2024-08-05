package com.eighteen.eighteenandroid.presentation.chat

import androidx.lifecycle.ViewModel
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor() : ViewModel() {
    private val _chatRoomsStateFlow =
        MutableStateFlow<ModelState<List<ChatRoom>>>(ModelState.Empty())

    val chatRoomsStateFlow = _chatRoomsStateFlow.asStateFlow()

    private val swipeStateMap = HashMap<String, Boolean>()

    fun getSwipeState(chatRoomId: String) = swipeStateMap[chatRoomId] ?: false

    fun setSwipeState(chatRoomId: String, isSwiped: Boolean) {
        swipeStateMap[chatRoomId] = isSwiped
    }

    fun exitChatRoom(chatRoomId: String) {
        //TODO 채팅창 나가기 api 호출
        swipeStateMap.remove(chatRoomId)
    }

    init {
        //TODO test 데이터 제거
        _chatRoomsStateFlow.value = ModelState.Success(List(50) {
            ChatRoom("$it", 123, 124, Date(), Date(), it, "message $it", Date())
        })
    }
}