package com.eighteen.eighteenandroid.presentation.myprofile.viewholder

import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemMyProfileTenOfQnaBinding
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.myprofile.MyProfileClickListener
import com.eighteen.eighteenandroid.presentation.myprofile.model.MyProfileItem

class MyProfileTenOfQnaViewHolder(
    private val clickListener: MyProfileClickListener,
    private val binding: ItemMyProfileTenOfQnaBinding
) : BaseMyProfileViewHolder<MyProfileItem.TenOfQna, ItemMyProfileTenOfQnaBinding>(binding = binding) {
    override fun onBind(model: MyProfileItem.TenOfQna) {
        with(binding) {
            llQna.isVisible = model.qnas.isNotEmpty()
            tvEmpty.isVisible = model.qnas.isEmpty()
            llQna.removeAllViews()
            ivBtnEditTenOfQna.setOnClickListener {
                clickListener.onClickEditTenOfQna()
            }
            bindQnaViews(model = model)

        }
    }

    private fun bindQnaViews(model: MyProfileItem.TenOfQna) {
        with(binding) {
            val displaySize =
                if (model.isExpanded) model.qnas.size else MIN_EXPANDABLE_ITEM_COUNT - 1
            model.qnas.take(displaySize).forEachIndexed { idx, qna ->
                //TODO qna 질문 변환 적용, 현재 임시로 name 사용
                val titleTextView = createTitleTextView(idx, qna.question.name)
                val contentTextView = createAnswerTextView(qna.answer)
                llQna.addView(titleTextView)
                llQna.addView(contentTextView)
            }

            if (model.qnas.size >= MIN_EXPANDABLE_ITEM_COUNT) {
                val stateTextView = createStateTextView(isExpanded = model.isExpanded)
                llQna.addView(stateTextView)
            }
        }

    }

    private fun createTitleTextView(index: Int, title: String) = TextView(context).apply {
        text = StringBuilder().append(index + 1).append(". ").append(title)
        setTextAppearance(R.style.pretendard_bold_16)
        setTextColor(ContextCompat.getColor(context, R.color.black))
        val marginTop = if (index == 0) 0 else context.dp2Px(16)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, marginTop, 0, 0)
        }
    }

    private fun createAnswerTextView(content: String) = TextView(context).apply {
        text = content
        setTextAppearance(R.style.pretendard_regular_14)
        setTextColor(ContextCompat.getColor(context, R.color.grey_02))
        val marginTop = context.dp2Px(7)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, marginTop, 0, 0)
        }
    }

    private fun createStateTextView(isExpanded: Boolean) = TextView(context).apply {
        //TODO drawable 파일 머지 후 적용
        val moreDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_more)
        moreDrawable?.let {
            DrawableCompat.setTint(it, ContextCompat.getColor(context, R.color.grey_01))
        }
        setCompoundDrawablesWithIntrinsicBounds(
            null,
            moreDrawable,
            null,
            null
        )
        compoundDrawablePadding = context.dp2Px(13)
        setTextAppearance(R.style.pretendard_regular_14)
        setTextColor(ContextCompat.getColor(context, R.color.grey_02))
        //TODO str res 적용
        text = if (isExpanded) "접기" else "펼치기"
        gravity = Gravity.CENTER
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            val marginTop = context.dp2Px(12)
            val marginBottom = context.dp2Px(10)
            setMargins(0, marginTop, 0, marginBottom)
        }
        setOnClickListener {
            clickListener.onClickExpandQna()
        }
    }

    companion object {
        private const val MIN_EXPANDABLE_ITEM_COUNT = 3
    }
}