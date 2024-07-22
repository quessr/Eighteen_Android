package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemMyProfileLinkBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileLinkViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileLinkBinding
) : BaseMyProfileViewHolder<MyProfileItem.Link, ItemMyProfileLinkBinding>(
    binding = binding
) {
    override fun onBind(model: MyProfileItem.Link) {
        with(binding) {
            tvEmptyLinks.isVisible = model.links.isEmpty()
            llLinks.isVisible = model.links.isNotEmpty()
            llLinks.removeAllViews()
            model.links.forEach {
                val linkTextView = createLinkTextView(it.linkUrl)
                llLinks.addView(linkTextView)
            }
            ivBtnEditLink.setOnClickListener {
                clickListener.onClickEditLink()
            }
        }
    }

    private fun createLinkTextView(link: String) = TextView(context).apply {
        text = link
        setTextAppearance(R.style.pretendard_regular_13)
        setTextColor(ContextCompat.getColor(context, R.color.black))
        val linkDrawable = ContextCompat.getDrawable(context, R.drawable.ic_link)
        linkDrawable?.let {
            DrawableCompat.setTint(it, ContextCompat.getColor(context, R.color.grey_01))
        }
        setCompoundDrawablesWithIntrinsicBounds(
            linkDrawable,
            null,
            null,
            null
        )
        compoundDrawablePadding = context.dp2Px(4)
        gravity = Gravity.CENTER_VERTICAL
        ellipsize = TextUtils.TruncateAt.END
        maxLines = 1
    }
}