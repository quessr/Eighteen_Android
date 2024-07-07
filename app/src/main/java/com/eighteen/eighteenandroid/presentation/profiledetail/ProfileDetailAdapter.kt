package com.eighteen.eighteenandroid.presentation.profiledetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import com.eighteen.eighteenandroid.presentation.profiledetail.viewholder.ProfileDetailViewHolder

class ProfileDetailAdapter : ListAdapter<ProfileDetailModel, ProfileDetailViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDetailViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        fun <VB : ViewBinding> inflaterBinding(bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB) =
            bindingInflater.invoke(inflater, parent, false)
        return when (viewType) {
            ITEM_TYPE_PROFILE_INFO -> {
                val binding = inflaterBinding(ItemProfileDetailInfoBinding::inflate)
                ProfileDetailViewHolder.Info(binding)
            }

            ITEM_TYPE_PROFILE_IMAGES -> {
                val binding = inflaterBinding(ItemProfileDetailImagesBinding::inflate)
                ProfileDetailViewHolder.Images(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type") // 코드 비교 해 볼것
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileDetailModel.ProfileInfo -> ITEM_TYPE_PROFILE_INFO
            is ProfileDetailModel.ProfileImages -> ITEM_TYPE_PROFILE_IMAGES
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ProfileDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private const val ITEM_TYPE_PROFILE_INFO = 1
        private const val ITEM_TYPE_PROFILE_IMAGES = 2
        private const val ITEM_TYPE_LIKE = 3
        private const val ITEM_TYPE_INTRODUCTION = 4
        private const val ITEM_TYPE_QNA = 5

        private val diffUtil = object : DiffUtil.ItemCallback<ProfileDetailModel>() {
            override fun areItemsTheSame(
                oldItem: ProfileDetailModel,
                newItem: ProfileDetailModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProfileDetailModel,
                newItem: ProfileDetailModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}