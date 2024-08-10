package com.eighteen.eighteenandroid.presentation.chat.chatroom

import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentChatRoomBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(FragmentChatRoomBinding::inflate) {


    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    companion object {
        const val ARGUMENT_CHAT_ROOM_ID_KEY = "ARGUMENT_CHAT_ROOM_ID_KEY"
    }
}