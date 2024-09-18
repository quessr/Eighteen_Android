package com.eighteen.eighteenandroid.presentation.teen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.databinding.ItemTeenBinding
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.UserDiffCallBack

interface TeenListAdapterListener {
    /** 유저 클릭 -> 유저 상세로 이동 */
    fun onUserClicks(user: User)

    /** 유저 좋아요 클릭 */
    fun onUserLikeClicks(user: User)

    /** 유저 채팅 클릭 */
    fun onUserChatClicks(user: User)

    /** 유저 더보기 클릭 */
    fun onUserMoreClicks(itemView: View, user: User)
}

class TeenListAdapter(private val listener: TeenListAdapterListener) : ListAdapter<User, TeenListAdapter.UserViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTeenBinding.inflate(inflater, parent, false)

        return UserViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class UserViewHolder(private val binding: ItemTeenBinding, private val listener: TeenListAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            with(binding) {
                val userName = "${user.userName}, ${user.userAge}"
                tvSchool.text = user.userSchoolName
                tvName.text = userName
                Glide.with(imgTodayTeen.context).load(user.userImage).into(imgTodayTeen) // 프로필 이미지

                imgTodayTeen.setOnClickListener {
                    listener.onUserClicks(user)
                }
                btnChat.setOnClickListener {
                    listener.onUserChatClicks(user)
                }
                btnLike.setOnClickListener {
                    listener.onUserLikeClicks(user)
                }
                btnSetting.setOnClickListener {
                    listener.onUserMoreClicks(btnSetting, user)
                }
            }
        }
    }
}