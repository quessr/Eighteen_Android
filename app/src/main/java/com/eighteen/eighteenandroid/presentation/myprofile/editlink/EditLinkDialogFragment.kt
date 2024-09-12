package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentEditLinkDialogBinding
import com.eighteen.eighteenandroid.domain.model.SnsInfo
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditLinkDialogFragment :
    BaseDialogFragment<FragmentEditLinkDialogBinding>(FragmentEditLinkDialogBinding::inflate) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    @Inject
    lateinit var editLinkViewModelAssistedFactory: EditLinkViewModel.EditLinkViewModelAssistedFactory
    private val editLinkViewModel by viewModels<EditLinkViewModel>(factoryProducer = {
        val snsLinksList = loginViewModel.myProfileStateFlow.value.data?.snsInfoList ?: emptyList()
        EditLinkViewModel.Factory(
            instagram = snsLinksList.find { it.type == SnsInfo.SnsType.INSTAGRAM }?.id.orEmpty(),
            x = snsLinksList.find { it.type == SnsInfo.SnsType.X }?.id.orEmpty(),
            tiktok = snsLinksList.find { it.type == SnsInfo.SnsType.TIKTOK }?.id.orEmpty(),
            youtube = snsLinksList.find { it.type == SnsInfo.SnsType.YOUTUBE }?.id.orEmpty(),
            assistedFactory = editLinkViewModelAssistedFactory
        )
    })

    override fun initView() {
        bind {
            with(editLinkViewModel.editLinkModelStateFlow.value) {
                etInstagram.run {
                    setText(instagram)
                    addTextChangedListener {
                        editLinkViewModel.setInstagram(it?.toString().orEmpty())
                    }
                }
                etTiktok.run {
                    setText(tiktok)
                    addTextChangedListener {
                        editLinkViewModel.setTiktok(it?.toString().orEmpty())
                    }
                }
                etX.run {
                    setText(this@with.x)
                    addTextChangedListener {
                        editLinkViewModel.setX(it?.toString().orEmpty())
                    }
                }
                etYoutube.run {
                    setText(youtube)
                    addTextChangedListener {
                        editLinkViewModel.setYoutube(it?.toString().orEmpty())
                    }
                }
            }
            ivBtnCheck.setOnClickListener {
                editLinkViewModel.requestEditLinks()
            }
            ivBtnBack.setOnClickListener {
                dismissNow()
            }
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(editLinkViewModel.editLinkModelStateFlow) { editLinkModel ->
            val context = context ?: return@collectInLifecycle
            val isEnabled = editLinkModel.run { listOf(instagram, x, tiktok, youtube) }
                .any { text -> text.isNotEmpty() }
            binding.ivBtnCheck.isEnabled = isEnabled
            val targetDrawable =
                ContextCompat.getDrawable(context, R.drawable.ic_check) ?: return@collectInLifecycle
            val colorTint =
                ContextCompat.getColor(context, if (isEnabled) R.color.black else R.color.grey_03)
            DrawableCompat.setTint(targetDrawable, colorTint)
            binding.ivBtnCheck.setImageDrawable(targetDrawable)
        }
        collectInLifecycle(editLinkViewModel.editLinkResultStateFlow) {
            when (it) {
                is ModelState.Loading -> {
                    //TODO 로딩처리
                }
                is ModelState.Success -> {
                    it.data?.getContentIfNotHandled()?.let { snsInfoList ->
                        loginViewModel.editSnsInfoList(snsInfoList)
                        dismissNow()
                    }
                }
                is ModelState.Error -> {
                    //TODO 에러처리
                }
                else -> {
                    //do nothing
                }
            }
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
        }
    }
}