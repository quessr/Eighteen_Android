package com.eighteen.eighteenandroid.presentation.chat.chatroom

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentChatRoomBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.ModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(FragmentChatRoomBinding::inflate) {

    @Inject
    lateinit var assistedFactory: ChatRoomViewModel.ChatRoomAssistedFactory
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
            ivBtnOption.setOnClickListener {
                //TODO 신고하기 dialog
            }
            tvBtnSend.setOnClickListener {
                chatRoomViewModel.requestSendMessage(message = etInput.text.toString())
                etInput.setText("")
            }
            rvChat.adapter = ChatMessagesAdapter()
            rvChat.addItemDecoration(ChatMessageItemDecoration())
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatRoomViewModel.chatRoomMessagesStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩처리
                        }
                        is ModelState.Success -> {
                            (binding.rvChat.adapter as? ChatMessagesAdapter)?.submitList(it.data)

                        }
                        is ModelState.Error -> {
                            //TODO 에러처리
                        }
                        else -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }


    companion object {
        const val ARGUMENT_CHAT_ROOM_ID_KEY = "ARGUMENT_CHAT_ROOM_ID_KEY"
        const val ARGUMENT_SENDER_ID_KEY = "SENDER_ID_KEY"
    }
}