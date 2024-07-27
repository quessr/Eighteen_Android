package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditLinkDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkPage
import kotlinx.coroutines.launch

//TODO back pressed dispatchers
class EditLinkDialogFragment :
    BaseDialogFragment<FragmentEditLinkDialogBinding>(FragmentEditLinkDialogBinding::inflate) {

    private val editLinkViewModel by viewModels<EditLinkViewModel>()

    override fun initView() {
        with(binding) {
            tvTitle.text = getString(R.string.my_profile_my_link)
            ivBtnClose.setOnClickListener {
                dismissNow()
            }
            rvLinks.addItemDecoration(EditLinkItemDecoration())

            rvLinks.adapter = EditLinkDialogAdapter(onClickRemove = {
                //TODO 링크 제거 api
            })
            tvBtnAddLink.setOnClickListener {
                editLinkViewModel.moveAddPage()
            }
        }
        initAddPageView()
        initStateFlow()
    }

    private fun initAddPageView() {
        with(binding) {
            ivBtnClose.setOnClickListener {
                editLinkViewModel.movePrevPage()
                etAddLinkUrl.addTextChangedListener {
                    setAddLinkCheckButtonEnabled(isEnabled = it.isNullOrBlank().not())
                }
                ivAddBtnCheck.setOnClickListener {
                    //TODO 링크 추가 api
                }
            }
        }
    }

    private fun setAddLinkCheckButtonEnabled(isEnabled: Boolean) {
        binding.ivAddBtnCheck.run {
            setEnabled(isEnabled)
            context?.let {
                val colorRes = if (isEnabled) R.color.black else R.color.grey_03
                setColorFilter(ContextCompat.getColor(it, colorRes))
            }
        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editLinkViewModel.editLinkPageStateFlow.collect {
                    binding.clAddLinkContainer.isVisible = it == EditLinkPage.ADD
                    binding.clMainContainer.isVisible = it == EditLinkPage.MAIN
                }
            }
        }
    }
}