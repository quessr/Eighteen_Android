package com.eighteen.eighteenandroid.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.domain.usecase.GetChatRoomsUseCase
import com.eighteen.eighteenandroid.presentation.chat.model.ChatRoomsModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val getChatRoomsUseCase: GetChatRoomsUseCase,
    @Assisted private val senderNo: Int
) : ViewModel() {
    private val chatRoomsStateFlow =
        MutableStateFlow<ModelState<List<ChatRoom>>>(ModelState.Empty())
    private val keywordStateFlow = MutableStateFlow("")

    private val swipeStateMap = HashMap<String, Boolean>()

    private var getChatRoomsJob: Job? = null

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

    init {
        requestChatRooms()
    }

    private fun requestChatRooms() {
        if (getChatRoomsJob?.isCompleted == false) return
        getChatRoomsJob = viewModelScope.launch {
            chatRoomsStateFlow.value = ModelState.Loading()
            getChatRoomsUseCase.invoke(senderNo = senderNo).onSuccess {
                chatRoomsStateFlow.value = ModelState.Success(it)
            }.onFailure {
                chatRoomsStateFlow.value = ModelState.Error(throwable = it)
            }
        }
    }

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

    @AssistedFactory
    interface ChatAssistedFactory {
        fun create(senderNo: Int): ChatViewModel
    }

    class Factory(
        private val assistedFactory: ChatAssistedFactory,
        private val senderNo: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(senderNo) as T
        }
    }
}