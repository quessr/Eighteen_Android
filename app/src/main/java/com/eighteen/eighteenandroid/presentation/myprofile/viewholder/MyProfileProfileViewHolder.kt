package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemMyProfileProfileBinding
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileProfileViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileProfileBinding
) : BaseMyProfileViewHolder<MyProfileItem.Profile, ItemMyProfileProfileBinding>(binding) {

    override fun onBind(model: MyProfileItem.Profile) {
        with(binding) {
            tvNickName.text = createNickString(nickName = model.nickName)

        }
    }

    private fun createNickString(nickName: String): SpannableString {
        val text = context.getString(R.string.my_profile_have_a_nice_day, nickName)
        return SpannableString(text).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context,
                        R.color.main_color
                    )
                ), 0, nickName.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(context, R.color.black)
                ),
                nickName.length,
                text.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}