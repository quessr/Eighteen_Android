package com.eighteen.eighteenandroid.presentation.badgedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentBadgeDetailDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.badgedetail.model.BadgeDetailModel
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader

class BadgeDetailDialogFragment :
    BaseDialogFragment<FragmentBadgeDetailDialogBinding>(FragmentBadgeDetailDialogBinding::inflate) {
    override fun initView() {
        bind {
            val model = arguments?.getParcelableOrNull(
                ARGUMENT_BADGE_DETAIL_MODEL_KEY,
                BadgeDetailModel::class.java
            ) ?: return@bind
            ivBtnClose.setOnClickListener {
                dismissNow()
            }
            ImageLoader.get().loadUrlCircleCrop(
                ivBadgeImage,
                url = model.imageUrl,
                placeHolderRes = R.drawable.bg_oval_divider
            )
            tvBadgeName.text = model.badgeName
            tvBadgeDescription.text = model.badgeDescription
        }
    }

    override fun onResume() {
        super.onResume()
        initDialogWindow()
    }

    private fun initDialogWindow() {
        dialog?.run {
            window?.run {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    companion object {
        private const val ARGUMENT_BADGE_DETAIL_MODEL_KEY = "argument_badge_detail_model_key"
        fun newInstance(model: BadgeDetailModel) = BadgeDetailDialogFragment().apply {
            arguments = bundleOf(ARGUMENT_BADGE_DETAIL_MODEL_KEY to model)
        }
    }
}