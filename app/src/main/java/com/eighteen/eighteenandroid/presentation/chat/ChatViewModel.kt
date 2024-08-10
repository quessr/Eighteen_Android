package com.eighteen.eighteenandroid.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.chat.model.ChatRoomsModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val chatRoomsStateFlow =
        MutableStateFlow<ModelState<List<ChatRoom>>>(ModelState.Empty())
    private val keywordStateFlow = MutableStateFlow("")

    private val swipeStateMap = HashMap<String, Boolean>()

    val chatRoomWithKeywordStateFlow =
        combine(chatRoomsStateFlow, keywordStateFlow) { chatRoom, keyword ->
            val filteredChatRooms =
                chatRoom.data?.filter { it.name.contains(keyword, true) } ?: emptyList()
            chatRoom.copyWithData(
                ChatRoomsModel(
                    keyword = keyword,
                    chatRooms = filteredChatRooms
                )
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ModelState.Empty())

    fun getSwipeState(chatRoomId: String) = swipeStateMap[chatRoomId] ?: false

    fun setSwipeState(chatRoomId: String, isSwiped: Boolean) {
        swipeStateMap[chatRoomId] = isSwiped
    }

    fun exitChatRoom(chatRoomId: String) {
        //TODO 채팅창 나가기 api 호출
        if (chatRoomsStateFlow.value !is ModelState.Success) return
        swipeStateMap.remove(chatRoomId)
        chatRoomsStateFlow.value =
            ModelState.Success(chatRoomsStateFlow.value.data?.toMutableList()?.apply {
                removeIf { it.chatRoomId == chatRoomId }
            })
    }

    fun setKeyword(keyword: String) {
        keywordStateFlow.value = keyword
    }

    init {
        //TODO test 데이터 제거
        viewModelScope.launch {
            delay(2000)
            chatRoomsStateFlow.value = ModelState.Success(data = List(50) {
                ChatRoom("$it", 123, 124, Date(), Date(), it, "message $it", Date(), name = "$it")
            })
        }

    }
}