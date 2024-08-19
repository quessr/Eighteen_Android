package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent.ACTION_DOWN
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditLinkDialogBinding
import com.eighteen.eighteenandroid.domain.model.SnsLink
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.model.EditLinkPage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditLinkDialogFragment :
    BaseDialogFragment<FragmentEditLinkDialogBinding>(FragmentEditLinkDialogBinding::inflate) {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val editLinkViewModel by viewModels<EditLinkViewModel>()

    override fun initView() {
        initMainPageView()
        initAddPageView()
        initStateFlow()
    }

    private fun initMainPageView() {
        with(binding) {
            tvTitle.text = getString(R.string.my_profile_my_link)
            ivBtnClose.setOnClickListener {
                dismissNow()
            }
            rvLinks.addItemDecoration(EditLinkItemDecoration())
            rvLinks.adapter = EditLinkDialogAdapter(
                onClickRemove = editLinkViewModel::requestRemoveLink,
                findAdapterPosition = rvLinks::getChildAdapterPosition
            )
            rvLinks.itemAnimator = null
            tvBtnAddLink.setOnClickListener {
                editLinkViewModel.moveAddPage()
                binding.etAddLinkUrl.setText("")
                binding.etAddLinkName.setText("")
            }
        }
    }

    private fun initAddPageView() {
        with(binding) {
            ivAddBtnBack.setOnClickListener {
                editLinkViewModel.movePrevPage()
            }
            etAddLinkUrl.addTextChangedListener {
                setAddLinkCheckButtonEnabled(isEnabled = it.isNullOrBlank().not())
            }
            ivAddBtnCheck.setOnClickListener {
                val snsLink = SnsLink(
                    linkUrl = etAddLinkUrl.text.toString(),
                    name = etAddLinkName.text.toString().takeIf { it.isNotEmpty() })
                editLinkViewModel.requestAddLink(snsLink = snsLink)
            }
            setAddLinkCheckButtonEnabled(
                isEnabled = etAddLinkUrl.text.toString().isBlank().not()
            )
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.myProfileStateFlow.collect {
                    val snsLinks = it.data?.snsLinks ?: emptyList()
                    (binding.rvLinks.adapter as? EditLinkDialogAdapter)?.submitList(snsLinks)
                    setAddExternalLinkButtonEnabled(isEnabled = snsLinks.size < MAXIMUM_LINK_SIZE)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editLinkViewModel.addLinkResultStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩 처리
                        }
                        is ModelState.Success -> {
                            it.data?.getContentIfNotHandled()?.let { snsLink ->
                                loginViewModel.addSnsLink(snsLink = snsLink)
                                if (editLinkViewModel.editLinkPageStateFlow.value == EditLinkPage.ADD) editLinkViewModel.movePrevPage()
                            }
                        }
                        is ModelState.Error -> {
                            //TODO error 처리
                        }
                        is ModelState.Empty -> {
                            //do nothing
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editLinkViewModel.removeLinkResultState.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO 로딩 처리
                        }
                        is ModelState.Success -> {
                            it.data?.getContentIfNotHandled()?.let { idx ->
                                loginViewModel.removeSnsLinkAt(idx = idx)
                            }
                        }
                        is ModelState.Error -> {
                            //TODO error 처리
                        }
                        is ModelState.Empty -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }

    private fun setAddExternalLinkButtonEnabled(isEnabled: Boolean) {
        with(binding.tvBtnAddLink) {
            this.isEnabled = isEnabled
            val textColor =
                ContextCompat.getColor(context, if (isEnabled) R.color.grey_02 else R.color.grey_03)
            setTextColor(textColor)
            val drawable = ContextCompat.getDrawable(
                context,
                if (isEnabled) R.drawable.ic_add_external_link else R.drawable.ic_add_external_link_disabled
            )
            setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
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
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.action == ACTION_DOWN) {
                    handleBackKeyEvent()
                    return@setOnKeyListener true
                }
                false
            }
        }
    }

    private fun handleBackKeyEvent() {
        if (editLinkViewModel.editLinkPageStateFlow.value == EditLinkPage.MAIN) dismissNow()
        else editLinkViewModel.movePrevPage()
    }

    companion object {
        //TODO 글로벌 변수 고려
        private const val MAXIMUM_LINK_SIZE = 3
    }
}