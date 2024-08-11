package com.eighteen.eighteenandroid.presentation.chat.chatroom

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentChatRoomBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(FragmentChatRoomBinding::inflate) {
    private lateinit var assistedFactory: ChatRoomViewModel.ChatRoomAssistedFactory
    private val chatRoomViewModel by viewModels<ChatRoomViewModel>(factoryProducer = {
        ChatRoomViewModel.Factory(
            assistedFactory = assistedFactory, chatRoomId = arguments?.getString(
                ARGUMENT_CHAT_ROOM_ID_KEY
            ) ?: "",
            senderId = arguments?.getInt(ARGUMENT_SENDER_ID_KEY, -1) ?: -1
        )
    })

    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    companion object {
        const val ARGUMENT_CHAT_ROOM_ID_KEY = "ARGUMENT_CHAT_ROOM_ID_KEY"
        const val ARGUMENT_SENDER_ID_KEY = "SENDER_ID_KEY"
    }
}