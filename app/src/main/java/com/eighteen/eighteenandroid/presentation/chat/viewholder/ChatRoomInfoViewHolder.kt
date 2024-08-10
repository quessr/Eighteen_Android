package com.eighteen.eighteenandroid.presentation.chat.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eighteen.eighteenandroid.databinding.ItemChatRoomInfoBinding
import com.eighteen.eighteenandroid.domain.model.ChatRoom
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class ChatRoomInfoViewHolder(
    private val binding: ItemChatRoomInfoBinding,
    private val onClick: (String) -> Unit
) : ViewHolder(binding.root) {

    fun onBind(model: ChatRoom) {
        with(binding) {
            root.setOnClickListener {
                onClick.invoke(model.chatRoomId)
            }
            //TODO 이미지 추가
            ImageLoader.get().loadUrl(
                ivChatRoomThumbnail,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
            )
            tvName.text = model.name
            tvLastChat.text = model.message
            tvCount.text = model.unreadMessageCount.toString()
            //TODO 시간포맷 적용
            tvTime.text = "15분 전"
        }
    }
}