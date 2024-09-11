package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import android.text.TextUtils
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemMyProfileLinkBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileLinkViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileLinkBinding
) : BaseMyProfileViewHolder<MyProfileItem.Link, ItemMyProfileLinkBinding>(binding = binding) {
    override fun onBind(model: MyProfileItem.Link) {
        with(binding) {
            llLinks.removeAllViews()
            tvEmptyLinks.isVisible = model.snsInfo.isEmpty()
            llLinks.isVisible = model.snsInfo.isNotEmpty()

            model.snsInfo.forEachIndexed { idx, snsInfo ->
                val view = createLinkTextView(
                    iconDrawableRes = snsInfo.type.iconDrawableRes,
                    text = snsInfo.id
                )
                llLinks.addView(view)
                if (idx < model.snsInfo.lastIndex) {
                    val dividerView = createDividerView()
                    llLinks.addView(dividerView)
                }
            }
        }
    }

    private fun createLinkTextView(@DrawableRes iconDrawableRes: Int, text: String) =
        TextView(context).apply {
            setText(text)
            setTextAppearance(R.style.pretendard_regular_13)
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setCompoundDrawablesWithIntrinsicBounds(iconDrawableRes, 0, 0, 0)
            compoundDrawablePadding = context.dp2Px(8)
            ellipsize = TextUtils.TruncateAt.END
            gravity = CENTER_VERTICAL
            maxLines = 1
        }

    private fun createDividerView() = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, context.dp2Px(1)).apply {
            setMargins(0, context.dp2Px(17), 0, context.dp2Px(11))
        }
        setBackgroundResource(R.color.grey_04)
    }
}