package com.eighteen.eighteenandroid.presentation.profiledetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemProfileDetailQnaBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import kotlinx.coroutines.launch

class QuestionAnswerAdapter(
    lifecycleOwner: LifecycleOwner,
    private val viewModel: ProfileDetailViewModel,
) :
    ListAdapter<ProfileDetailModel.Qna, QuestionAnswerAdapter.ViewHolder>(diffUtil) {

    private var items: List<ProfileDetailModel.Qna> = emptyList()



    init {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { newItems ->
                items = newItems.filterIsInstance<ProfileDetailModel.Qna>()
                notifyDataSetChanged()
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            viewModel.showAllItems.collect {
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileDetailQnaBinding.inflate(inflater, parent, false)

        return ViewHolder(binding).also {
            binding.ivSeeMore.setOnClickListener {
                viewModel.toggleItems()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < items.size) {
            val item = items[position]
            holder.bind(item.question, item.answer)
        }

        val showAllItems = viewModel.showAllItems.value
        val itemCountThreshold = ProfileDetailViewModel.ITEM_COUNT_THRESHOLD

        // 마지막 아이템 뒤에만 "펼치기"/"접기" 버튼을 보이게 설정
        if (position == itemCountThreshold - 1 && !showAllItems && items.size > itemCountThreshold) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("펼쳐서 보기")
            holder.seeSeeMoreClickListener { viewModel.toggleItems() }
        } else if (position == items.size - 1 && showAllItems) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("접기")
            holder.seeSeeMoreClickListener { viewModel.toggleItems() }
        } else {
            holder.showSeeMore(false)
        }
    }

    override fun getItemCount(): Int {
        return viewModel.getItemCount()
    }

    class ViewHolder(private val binding: ItemProfileDetailQnaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String, answer: String) {
            binding.question.text = question
            binding.answer.text = answer
        }

        fun showSeeMore(show: Boolean) {
            binding.ivSeeMore.isVisible = show
            binding.tvSeeMore.isVisible = show
        }

        fun setSeeMoreText(text: String) {
            binding.tvSeeMore.text = text
        }

        fun seeSeeMoreClickListener(listener: () -> Unit) {
            binding.tvSeeMore.setOnClickListener { listener() }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ProfileDetailModel.Qna>() {
            override fun areItemsTheSame(
                oldItem: ProfileDetailModel.Qna,
                newItem: ProfileDetailModel.Qna
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProfileDetailModel.Qna,
                newItem: ProfileDetailModel.Qna
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}