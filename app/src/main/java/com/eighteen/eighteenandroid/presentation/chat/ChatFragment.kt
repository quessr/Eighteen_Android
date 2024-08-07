package com.eighteen.eighteenandroid.presentation.chat

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentChatBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.hideKeyboardAndRemoveCurrentFocus
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import kotlinx.coroutines.launch

//TODO 채팅방 입장, 나가기 dialog, 나가기
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val chatRoomViewModel by viewModels<ChatRoomViewModel>()
    override fun initView() {
        bind {
            rvChatRooms.adapter = ChatRoomsAdapter(
                setSwipeState = chatRoomViewModel::setSwipeState,
                getSwipeState = chatRoomViewModel::getSwipeState,
                onClickExit = ::onClickExitBtn
            )
            rvChatRooms.itemAnimator = null
            root.setOnClickListener {
                hideKeyboardAndRemoveCurrentFocus()
            }
            etSearch.setText(
                chatRoomViewModel.chatRoomWithKeywordStateFlow.value.data?.keyword ?: ""
            )
            etSearch.addTextChangedListener {
                chatRoomViewModel.setKeyword(it?.run { toString() } ?: "")
            }
        }
        initStateFlow()
        initFragmentResultListener()
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatRoomViewModel.chatRoomWithKeywordStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩
                        }
                        is ModelState.Success -> {
                            //TODO 비어있을 경우 처리
                            (binding.rvChatRooms.adapter as? ChatRoomsAdapter)?.submitList(
                                it.data?.chatRooms ?: emptyList()
                            )
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

    private fun onClickExitBtn(chatRoomId: String) {
        val exitDialog = ChatRoomExitDialogFragment.newInstance(
            requestKey = REQUEST_KEY_CHAT_ROOM_EXIT,
            id = chatRoomId
        )
        showDialogFragment(exitDialog)
    }

    private fun initFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_CHAT_ROOM_EXIT,
            viewLifecycleOwner,
            object : ChatRoomExitDialogFragment.ResultListener() {
                override fun onConfirm(id: String) {
                    chatRoomViewModel.exitChatRoom(chatRoomId = id)
                }
            }
        )
    }

    companion object {
        private const val REQUEST_KEY_CHAT_ROOM_EXIT = "REQUEST_KEY_CHAT_ROOM_EXIT"
    }
}