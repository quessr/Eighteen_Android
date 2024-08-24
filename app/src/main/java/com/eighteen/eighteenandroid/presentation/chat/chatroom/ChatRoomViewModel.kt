package com.eighteen.eighteenandroid.presentation.chat.chatroom

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eighteen.eighteenandroid.common.HOUR_MINUTE_FORMAT_STRING
import com.eighteen.eighteenandroid.common.KOREA_YEAR_MONTH_DAY_STRING
import com.eighteen.eighteenandroid.common.safeLet
import com.eighteen.eighteenandroid.common.toCalendar
import com.eighteen.eighteenandroid.common.toTimeString
import com.eighteen.eighteenandroid.domain.model.ChatMessage
import com.eighteen.eighteenandroid.domain.usecase.GetChatMessagesUseCase
import com.eighteen.eighteenandroid.presentation.chat.chatroom.model.ChatRoomMessageModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class ChatRoomViewModel @AssistedInject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    @Assisted private val chatRoomId: String,
    @Assisted private val senderId: Int
) : ViewModel() {

    private val _chatRoomMessagesStateFlow =
        MutableStateFlow<ModelState<List<ChatRoomMessageModel>>>(ModelState.Empty())

    val chatRoomMessagesStateFlow = _chatRoomMessagesStateFlow.asStateFlow()

    private var getChatMessagesJob: Job? = null
    var isInitialized = false

    init {
        requestChatMessages()
    }

    //TODO 채팅 내역 위로 스크롤 할 경우 요청에 커서 필요할 것 같음
    fun requestChatMessages() {
        if (getChatMessagesJob?.isCompleted == false) return
        getChatMessagesJob = viewModelScope.launch {
            val prevData = chatRoomMessagesStateFlow.value.data ?: emptyList()
            _chatRoomMessagesStateFlow.value = ModelState.Loading(prevData)

            getChatMessagesUseCase.invoke(
                chatRoomId = chatRoomId,
                requestTime = Date().toTimeString()
            ).onSuccess {
                _chatRoomMessagesStateFlow.value =
                    ModelState.Success(createMessageModels(prevItem = prevData, newData = it))
            }.onFailure {
                ModelState.Error(data = prevData, throwable = it)
            }
        }
    }

    //TODO 날짜 변경 표시 어떻게 할지? + 알림(경고) 아이템 추가 언제 하는지?, image 추가
    private fun createMessageModels(
        prevItem: List<ChatRoomMessageModel>,
        newData: List<ChatMessage>
    ): List<ChatRoomMessageModel> {
        fun isSameDay(calendar: Calendar, other: Calendar): Boolean {
            val isSameYear = calendar.get(Calendar.YEAR) == other.get(Calendar.YEAR)
            val isSameYearOfDay =
                calendar.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
            return isSameYear && isSameYearOfDay
        }

        fun createTimeItemIfAnotherDayOrNull(
            calendar: Calendar?,
            other: Calendar?
        ): ChatRoomMessageModel.Time? {
            return safeLet(calendar, other) { safeCalendar, safeOther ->
                if (isSameDay(safeCalendar, safeOther).not()) {
                    val timeString = safeOther.time.toTimeString(KOREA_YEAR_MONTH_DAY_STRING)
                    ChatRoomMessageModel.Time(timeString = timeString)
                } else null
            }
        }

        var lastDateCalendar: Calendar? = null
        val items = mutableListOf<ChatRoomMessageModel>().apply {
            newData.forEach {
                val next = it.createdAt?.toCalendar()
                createTimeItemIfAnotherDayOrNull(lastDateCalendar, next)?.let { timeItem ->
                    add(timeItem)
                }
                val messageTime = it.createdAt?.toTimeString(HOUR_MINUTE_FORMAT_STRING) ?: ""
                val messageItem = if (it.senderNo == senderId) ChatRoomMessageModel.Message.Send(
                    message = it.message,
                    messageTimeString = messageTime,
                    createdAt = it.createdAt ?: lastDateCalendar?.time
                )
                else ChatRoomMessageModel.Message.Receive(
                    message = it.message,
                    messageTimeString = messageTime,
                    createdAt = it.createdAt ?: lastDateCalendar?.time,
                    imageUrl = ""
                )
                add(messageItem)
                next?.let { nextCalendar ->
                    lastDateCalendar = nextCalendar
                }
            }
            val prevFirstDateCalendar =
                prevItem.filterIsInstance<ChatRoomMessageModel.Message>()
                    .firstOrNull { it.createdAt != null }?.createdAt?.toCalendar() ?: return@apply
            createTimeItemIfAnotherDayOrNull(
                lastDateCalendar,
                prevFirstDateCalendar
            )?.let { timeItem ->
                add(timeItem)
            }
        }
        return items
    }

    fun requestSendMessage(message: String) {
        //TODO 메시지 전송
        Log.d("ChatRoomViewModel", "send message : $message")
    }

    @AssistedFactory
    interface ChatRoomAssistedFactory {
        fun create(chatRoomId: String, senderId: Int): ChatRoomViewModel
    }

    class Factory(
        private val assistedFactory: ChatRoomAssistedFactory,
        private val chatRoomId: String,
        private val senderId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(chatRoomId = chatRoomId, senderId = senderId) as T
        }
    }
}