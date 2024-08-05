package com.eighteen.eighteenandroid.presentation.chat

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentChatBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.ModelState
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val chatRoomViewModel by viewModels<ChatRoomViewModel>()
    override fun initView() {
        binding.rvChatRooms.adapter = ChatRoomsAdapter(
            setSwipeState = chatRoomViewModel::setSwipeState,
            getSwipeState = chatRoomViewModel::getSwipeState,
            onClickExit = ::onClickExit
        )
        initStateFlow()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatRoomViewModel.chatRoomsStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩
                        }
                        is ModelState.Success -> {
                            //TODO 비어있을 경우 처리
                            (binding.rvChatRooms.adapter as? ChatRoomsAdapter)?.submitList(it.data)
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

    private fun onClickExit(chatRoomId: String) {
        //TODO 나가기 dialog
    }
}