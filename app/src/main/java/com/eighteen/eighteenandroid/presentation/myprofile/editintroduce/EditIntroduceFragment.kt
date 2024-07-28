package com.eighteen.eighteenandroid.presentation.myprofile.editintroduce

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentEditIntroduceBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.myprofile.editintroduce.model.EditIntroducePage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//TODO mbti item decoration, onBackPressed
@AndroidEntryPoint
class EditIntroduceFragment :
    BaseFragment<FragmentEditIntroduceBinding>(FragmentEditIntroduceBinding::inflate) {

    private val editIntroduceViewModel by viewModels<EditIntroduceViewModel>()
    override fun initView() {
        initMbtiView()
        initDescriptionView()
        initStateFlow()
    }

    private fun initMbtiView() {
        bind {
            rvMbti.adapter =
                EditIntroduceMbtiAdapter(onClickItem = editIntroduceViewModel::toggleMbtiSelected)
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
}