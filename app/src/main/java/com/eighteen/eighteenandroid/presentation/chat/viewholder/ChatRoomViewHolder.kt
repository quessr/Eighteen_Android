package com.eighteen.eighteenandroid.presentation.chat.viewholder

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemChatRoomBinding
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.chat.ChatRoomAdapter

class ChatRoomViewHolder(
    private val binding: ItemChatRoomBinding,
    private val setSwipeState: (String, Boolean) -> Unit,
    private val getSwipeState: (String) -> Boolean,
    private val onClickExit: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var targetModel: ChatRoom? = null

    fun onCreateViewHolder() {
        with(binding.rvChatRoom) {
            adapter = ChatRoomAdapter(onClickExit = onClickExit)
            val snapHelper = object : PagerSnapHelper() {
                override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
                    val exitViewWidth = resources.getDimensionPixelOffset(R.dimen.chat_exit_width)
                    val infoView = layoutManager?.findViewByPosition(0) ?: return null
                    val targetPosition =
                        if (kotlin.math.abs(infoView.x) < exitViewWidth / 2) 0 else 1
                    targetModel?.let {
                        setSwipeState(it.chatRoomId, targetPosition == 1)
                    }
                    return layoutManager.getChildAt(targetPosition)
                }
            }
            snapHelper.attachToRecyclerView(this)
        }
    }

    fun onBind(model: ChatRoom) {
        targetModel = model
        val position = if (getSwipeState(model.chatRoomId)) 1 else 0
        with(binding.rvChatRoom) {
            (adapter as? ChatRoomAdapter)?.setData(chatRoom = model)
            scrollToPosition(position)
        }
    }
}