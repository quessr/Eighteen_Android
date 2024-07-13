package com.eighteen.eighteenandroid.presentation.profiledetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.databinding.ItemQnaBinding
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import kotlinx.coroutines.launch

class QuestionAnswerAdapter(
    lifecycleOwner: LifecycleOwner,
    private val viewModel: ProfileDetailViewModel,
) :
    ListAdapter<ProfileDetailModel.Qna, QuestionAnswerAdapter.ViewHolder>(diffUtil) {

    private var qnaItems: List<ProfileDetailModel.Qna> = emptyList()



    init {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { newItems ->
                val qnaLists = newItems.filterIsInstance<ProfileDetailModel.QnaList>()
                qnaItems = qnaLists.flatMap { it.qnas }
                Log.d("QuestionAnswerAdapter", "qnaItems size: ${qnaItems.size}")
                submitList(qnaItems)
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
        val binding = ItemQnaBinding.inflate(inflater, parent, false)

        return ViewHolder(binding).also {
            binding.ivSeeMore.setOnClickListener {
                viewModel.toggleItems()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        val showAllItems = viewModel.showAllItems.value
        val itemCountThreshold = ProfileDetailViewModel.ITEM_COUNT_THRESHOLD

        Log.d("Qna", "Qna items size: ${qnaItems.size}")

        // 마지막 아이템 뒤에만 "펼치기"/"접기" 버튼을 보이게 설정
        if (position == itemCountThreshold - 1 && !showAllItems && qnaItems.size > itemCountThreshold) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("펼쳐서 보기")
            holder.seeSeeMoreClickListener { viewModel.toggleItems() }
        } else if (position == qnaItems.size - 1 && showAllItems) {
            holder.showSeeMore(true)
            holder.setSeeMoreText("접기")
            holder.seeSeeMoreClickListener { viewModel.toggleItems() }
        } else {
            holder.showSeeMore(false)
        }
    }

    override fun getItemCount(): Int {
        val showAllItems = viewModel.showAllItems.value
        val itemCountThreshold = ProfileDetailViewModel.ITEM_COUNT_THRESHOLD
        return if (showAllItems) {
            qnaItems.size
        } else {
            minOf(qnaItems.size, itemCountThreshold)
        }
    }

    class ViewHolder(private val binding: ItemQnaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(qna: ProfileDetailModel.Qna) {
            binding.question.text = qna.question
            binding.answer.text = qna.answer
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