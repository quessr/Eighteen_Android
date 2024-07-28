package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.eighteen.eighteenandroid.databinding.FragmentEditIntroduceBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.common.recyclerview.GridMarginItemDecoration
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroducePage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditIntroduceFragment :
    BaseFragment<FragmentEditIntroduceBinding>(FragmentEditIntroduceBinding::inflate) {

    private val editIntroduceViewModel by viewModels<EditIntroduceViewModel>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (editIntroduceViewModel.pageStateFlow.value) {
                EditIntroducePage.MBTI -> findNavController().popBackStack()
                EditIntroducePage.DESCIRPTION -> editIntroduceViewModel.movePrevPage()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }

    override fun initView() {
        initMbtiView()
        initDescriptionView()
        initStateFlow()
    }

    private fun initMbtiView() {
        bind {
            rvMbti.adapter =
                EditIntroduceMbtiAdapter(onClickItem = editIntroduceViewModel::toggleMbtiSelected)
            val betweenMarginPx = root.context.dp2Px(MARGIN_BETWEEN_MBTI_ITEM_PX)
            rvMbti.layoutManager = GridLayoutManager(root.context, SPAN_COUNT_MBTI_ITEM)
            rvMbti.addItemDecoration(
                GridMarginItemDecoration(
                    spanCount = SPAN_COUNT_MBTI_ITEM,
                    horizontalBetweenMarginPx = betweenMarginPx,
                    verticalBetweenMarginPx = betweenMarginPx
                )
            )
        }
    }

    private fun initDescriptionView() {
        bind {

        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editIntroduceViewModel.pageStateFlow.collect {
                    binding.clEditMbtiContainer.isVisible = it == EditIntroducePage.MBTI
                    binding.clEditDescriptionContainer.isVisible =
                        it == EditIntroducePage.DESCIRPTION
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editIntroduceViewModel.mbtiModelsStateFlow.collect {
                    (binding.rvMbti.adapter as? EditIntroduceMbtiAdapter)?.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        onBackPressedCallback.remove()
        super.onDestroyView()
    }

    companion object {
        private const val SPAN_COUNT_MBTI_ITEM = 2
        private const val MARGIN_BETWEEN_MBTI_ITEM_PX = 16
    }
}