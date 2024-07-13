package com.eighteen.eighteenandroid.presentation.profiledetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailQnaBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import com.eighteen.eighteenandroid.presentation.profiledetail.viewholder.ProfileDetailViewHolder

class ProfileDetailAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ProfileDetailViewModel
) : ListAdapter<ProfileDetailModel, ProfileDetailViewHolder>(diffUtil) {


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

            ITEM_TYPE_BADGE_AND_TEEN -> {
                val binding = inflaterBinding(ItemProfileDetailBadgeAndTeenBinding::inflate)
                ProfileDetailViewHolder.BadgeAndTeen(binding)
            }

            ITEM_TYPE_INTRODUCTION -> {
                val binding = inflaterBinding(ItemProfileDetailIntroductionBinding::inflate)
                ProfileDetailViewHolder.Introduction(binding)
            }

            ITEM_TYPE_QNA_LIST -> {
                val binding = inflaterBinding(ItemProfileDetailQnaBinding::inflate)
                ProfileDetailViewHolder.Qna(binding, lifecycleOwner, viewModel)
            }

            else -> throw IllegalArgumentException("Invalid view type") // 코드 비교 해 볼것
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileDetailModel.ProfileInfo -> ITEM_TYPE_PROFILE_INFO
            is ProfileDetailModel.ProfileImages -> ITEM_TYPE_PROFILE_IMAGES
            is ProfileDetailModel.BadgeAndTeen -> ITEM_TYPE_BADGE_AND_TEEN
            is ProfileDetailModel.Introduction -> ITEM_TYPE_INTRODUCTION
            is ProfileDetailModel.QnaList -> ITEM_TYPE_QNA_LIST
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ProfileDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private const val ITEM_TYPE_PROFILE_INFO = 1
        private const val ITEM_TYPE_PROFILE_IMAGES = 2
        private const val ITEM_TYPE_BADGE_AND_TEEN = 3
        private const val ITEM_TYPE_LIKE = 4
        private const val ITEM_TYPE_INTRODUCTION = 5
        private const val ITEM_TYPE_QNA_LIST = 6

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