package com.eighteen.eighteenandroid.presentation.profiledetail

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesWithLikeBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaTitleBinding
import com.eighteen.eighteenandroid.databinding.ItemSeeMoreBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px
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
                val binding = inflaterBinding(ItemProfileDetailImagesWithLikeBinding::inflate)
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

            ITEM_TYPE_QNA_LIST_TITLE -> {
                val binding = inflaterBinding(ItemQnaTitleBinding::inflate)
                ProfileDetailViewHolder.QnaTitle(binding)
            }

            ITEM_TYPE_QNA -> {
                val binding = inflaterBinding(ItemQnaBinding::inflate)
                ProfileDetailViewHolder.Qna(binding)
            }

            ITEM_TYPE_QNA_TOGGLE -> {
                val binding = inflaterBinding(ItemSeeMoreBinding::inflate)
                ProfileDetailViewHolder.QnaToggle(binding, viewModel)
            }


//            ITEM_TYPE_QNA_LIST -> {
//                val binding = inflaterBinding(ItemProfileDetailQnaBinding::inflate)
//                ProfileDetailViewHolder.Qna(binding, lifecycleOwner, viewModel)
//            }

            else -> throw IllegalArgumentException("Invalid view type") // 코드 비교 해 볼것
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProfileDetailModel.ProfileInfo -> ITEM_TYPE_PROFILE_INFO
            is ProfileDetailModel.ProfileImages -> ITEM_TYPE_PROFILE_IMAGES
            is ProfileDetailModel.BadgeAndTeen -> ITEM_TYPE_BADGE_AND_TEEN
            is ProfileDetailModel.Introduction -> ITEM_TYPE_INTRODUCTION
            is ProfileDetailModel.QnaListTitle -> ITEM_TYPE_QNA_LIST_TITLE
            is ProfileDetailModel.Qna -> ITEM_TYPE_QNA
            is ProfileDetailModel.QnaToggle -> ITEM_TYPE_QNA_TOGGLE
//            is ProfileDetailModel.QnaList -> ITEM_TYPE_QNA_LIST
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ProfileDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))


        if (holder is ProfileDetailViewHolder.Qna) {
            Log.d("ProfileDetailAdapter", "itemCount $itemCount")

            when {
                position == 5 -> {
                    // 첫 번째 Qna 항목의 상단에 radius를 적용
                    holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_top)
                }
                position == itemCount - 1 && itemCount - 5 <= ProfileDetailViewModel.ITEM_COUNT_THRESHOLD  -> {
                    // Qna 항목이 ITEM_COUNT_THRESHOLD 이하인 경우 마지막 Qna 항목의 하단에 radius를 적용
                    holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_bottom)

                    // marginBottom 설정
                    val layoutParams = holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.bottomMargin = holder.binding.root.context.dp2Px(80)
                    holder.binding.root.layoutParams = layoutParams
                    Log.d("ProfileDetailAdapter", "Applied rounded_bottom to last Qna item at position $position")
                }
                else -> {
                    // 나머지 Qna 항목
                    holder.binding.root.setBackgroundColor(Color.parseColor("#999999")) // 배경색 설정
                    Log.d("ProfileDetailAdapter", "Applied background color to middle Qna item at position $position")
                }
            }
        } else if (holder is ProfileDetailViewHolder.QnaToggle) {
            // Qna 항목이 3개 이상인 경우 QnaToggle 항목의 하단에 radius를 적용
            holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_bottom)
            Log.d("ProfileDetailAdapter", "Applied rounded_bottom to QnaToggle at position $position")
        }
    }

    companion object {
        private const val ITEM_TYPE_PROFILE_INFO = 1
        private const val ITEM_TYPE_PROFILE_IMAGES = 2
        private const val ITEM_TYPE_BADGE_AND_TEEN = 3
        private const val ITEM_TYPE_LIKE = 4
        private const val ITEM_TYPE_INTRODUCTION = 5
        private const val ITEM_TYPE_QNA_LIST = 6
        private const val ITEM_TYPE_QNA_LIST_TITLE = 7
        private const val ITEM_TYPE_QNA = 8
        private const val ITEM_TYPE_QNA_TOGGLE = 9


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