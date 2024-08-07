package com.eighteen.eighteenandroid.presentation.profiledetail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailBadgeAndTeenBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailImagesWithLikeBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailInfoBinding
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailIntroductionBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaBinding
import com.eighteen.eighteenandroid.databinding.ItemQnaTitleBinding
import com.eighteen.eighteenandroid.databinding.ItemSeeMoreBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.common.media3.viewpager2.ViewPagerPlayerManager
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import com.eighteen.eighteenandroid.presentation.profiledetail.viewholder.ProfileDetailViewHolder

class ProfileDetailAdapter(
    private val viewModel: ProfileDetailViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val pageCallbackForMute: ViewPager2.OnPageChangeCallback
) : ListAdapter<ProfileDetailModel, ProfileDetailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDetailViewHolder {
        val context = parent.context
        val inflate = LayoutInflater.from(context)

        fun <VB : ViewBinding> inflaterBinding(bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB) =
            bindingInflater.invoke(inflate, parent, false)
        return when (viewType) {
            ITEM_TYPE_PROFILE_INFO -> {
                val binding = inflaterBinding(ItemProfileDetailInfoBinding::inflate)
                ProfileDetailViewHolder.Info(binding)
            }

            ITEM_TYPE_PROFILE_IMAGES -> {
                val binding = inflaterBinding(ItemProfileDetailImagesWithLikeBinding::inflate)
                ProfileDetailViewHolder.Images(
                    binding,
                    onPageChangeCallback = { currentPosition ->
                        viewModel.setCurrentPosition(currentPosition)
                    }, onLikeClickCallback = {
                        viewModel.toggleLike()
                    }, lifecycleOwner = lifecycleOwner,
                    pageCallbackForMute = pageCallbackForMute
                )
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
                ProfileDetailViewHolder.QnaToggle(binding) { toggle ->
                    viewModel.toggleItems()
                    viewModel.updateQnaToggle(toggle.copy(isExpanded = !toggle.isExpanded))
                }
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
            is ProfileDetailModel.QnaListTitle -> ITEM_TYPE_QNA_LIST_TITLE
            is ProfileDetailModel.Qna -> ITEM_TYPE_QNA
            is ProfileDetailModel.QnaToggle -> ITEM_TYPE_QNA_TOGGLE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ProfileDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))

        if (holder is ProfileDetailViewHolder.Images) {
                holder.binding.viewPager.setCurrentItem(
                    viewModel.currentPosition.value,
                    false
                ) // 저장된 위치로 설정
        }


        if (holder is ProfileDetailViewHolder.Qna) {

            when {
                position == 5 -> {
                    // 첫 번째 Qna 항목의 상단에 radius를 적용
                    holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_top)
                }

                position == itemCount - 1 && itemCount - 5 <= ProfileDetailViewModel.ITEM_COUNT_THRESHOLD -> {
                    // Qna 항목이 ITEM_COUNT_THRESHOLD 이하인 경우 마지막 Qna 항목의 하단에 radius 적용
                    holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_bottom)

                    // marginBottom 설정
                    val layoutParams =
                        holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.bottomMargin = holder.binding.root.context.dp2Px(80)
                    holder.binding.root.layoutParams = layoutParams
                }

                else -> {
                    // 나머지 Qna 항목
                    holder.binding.root.setBackgroundColor(Color.parseColor("#999999")) // 배경색 설정
                }
            }
        } else if (holder is ProfileDetailViewHolder.QnaToggle) {
            // Qna 항목이 3개 이상인 경우 QnaToggle 항목의 하단에 radius를 적용
            holder.binding.root.setBackgroundResource(R.drawable.bg_qna_rounded_bottom)
        }
    }

    companion object {
        private const val ITEM_TYPE_PROFILE_INFO = 1
        private const val ITEM_TYPE_PROFILE_IMAGES = 2
        private const val ITEM_TYPE_BADGE_AND_TEEN = 3
        private const val ITEM_TYPE_LIKE = 4
        private const val ITEM_TYPE_INTRODUCTION = 5
        private const val ITEM_TYPE_QNA_LIST_TITLE = 6
        private const val ITEM_TYPE_QNA = 7
        private const val ITEM_TYPE_QNA_TOGGLE = 8


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