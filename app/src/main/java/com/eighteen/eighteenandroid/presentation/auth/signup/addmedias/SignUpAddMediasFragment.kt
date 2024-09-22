package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import android.net.Uri
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.common.safeLet
import com.eighteen.eighteenandroid.databinding.FragmentSignUpAddMediasBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMediaItemModel
import com.eighteen.eighteenandroid.presentation.auth.signup.addmedias.model.SignUpMediaPickerWrapper
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpEditMediaAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpMedia
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpMedias
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpPage
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.getMimeTypeFromUri
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment

class SignUpAddMediasFragment :
    BaseSignUpContentFragment<FragmentSignUpAddMediasBinding>(FragmentSignUpAddMediasBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.setPageClearEvent(page = SignUpPage.ADD_MEDIAS)
        signUpViewModelContentInterface.setPageClearEvent(page = SignUpPage.SELECT_TAG)
        super.onMovePrevPageAction()
    }

    override val onMoveNextPageAction: () -> Unit = {
        signUpViewModelContentInterface.requestSignUp()
    }
    override val progress: Int = 100
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.PASS
    )

    private val mediaPickerCallback = { uri: Uri?, isRef: Boolean ->
        safeLet(uri, context) { nonNullUri, context ->
            getMimeTypeFromUri(nonNullUri, context)?.let { typeString ->
                val uriString = nonNullUri.toString()
                if (typeString.startsWith("image")) SignUpEditMediaAction.EditImage(
                    uriString,
                    isRef
                )
                else if (typeString.startsWith("video")) SignUpEditMediaAction.EditVideo(
                    uriString,
                    isRef
                )
                else null
            }?.also { action ->
                signUpViewModelContentInterface.setEditMediaAction(action)
            }
        }
    }

    private val mediaPickerWrapper = SignUpMediaPickerWrapper(this)

    private val clickListener = object : SignUpAddMediasClickListener {
        override fun onClickAddMedia() {
            mediaPickerWrapper.openMediaPicker { mediaPickerCallback(it, false) }
        }

        override fun onClickAddRefMedia() {
            mediaPickerWrapper.openMediaPicker { mediaPickerCallback(it, true) }
        }

        override fun onClickRemove(position: Int) {
            signUpViewModelContentInterface.removeMedia(position = position)
        }

        override fun onClickRemoveRef() {
            signUpViewModelContentInterface.removeRefMedia()
        }
    }

    override fun initView() {
        initRecyclerView()
        binding.tvBtnGuide.setOnClickListener {
            showDialogFragment(SignUpAddMediasGuideDialogFragment())
        }
        initTitle()
        initStateFlow()
    }

    private fun initTitle() {
        val context = context ?: return
        val tagString = signUpViewModelContentInterface.tag?.strValue ?: ""
        val titleText = buildSpannedString {
            val text = getString(
                R.string.sign_up_add_media_title,
                tagString
            )
            append(text)
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_01)),
                0,
                tagString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvTitle.text = titleText
    }

    private fun initRecyclerView() {
        bind {
            rvMedias.adapter = SignUpMediasAdapter(clickListener = clickListener)
            rvMedias.addItemDecoration(SignUpMediasItemDecoration())
            rvMedias.itemAnimator = null
        }
    }

    private fun initStateFlow() {
        collectInLifecycle(signUpViewModelContentInterface.mediasStateFlow) {
            (binding.rvMedias.adapter as? SignUpMediasAdapter)?.run {
                val mediaItemModels = createSignUpMediaItemModels(it)
                submitList(mediaItemModels)
            }
        }

        collectInLifecycle(signUpViewModelContentInterface.pageClearEvent) {
            if (it.peekContent() == SignUpPage.ADD_MEDIAS) {
                it.getContentIfNotHandled()?.run {
                    signUpViewModelContentInterface.clearMediaResultStateFlow()
                }
            }
        }
        collectInLifecycle(signUpViewModelContentInterface.signUpResultStateFlow) {
            when (it) {
                is ModelState.Loading -> {
                    Log.d("SignUpCompletedFragment", "loading")
                    //TODO 로딩 처리
                }
                is ModelState.Error -> {
                    Log.d("SignUpCompletedFragment", "error $it")
                    //TODO 에러처리
                }
                else -> {
                    Log.d("SignUpCompletedFragment", "Empty or Success")
                    //TODO 로딩, 에러 뷰 invisible
                }
            }
        }
    }

    private fun createSignUpMediaItemModels(signUpMedias: SignUpMedias): List<SignUpMediaItemModel> {
        fun createSignUpMediaItem(signUpMedia: SignUpMedia, isRef: Boolean = false) =
            when (signUpMedia) {
                is SignUpMedia.Image -> SignUpMediaItemModel.Image(
                    imageBitmap = signUpMedia.imageBitmap,
                    isRef = isRef
                )
                is SignUpMedia.Video -> SignUpMediaItemModel.Video(
                    uriString = signUpMedia.uriString,
                    isRef = isRef
                )
            }

        val items = mutableListOf<SignUpMediaItemModel>()
        val refItem = if (signUpMedias.ref != null) createSignUpMediaItem(
            signUpMedia = signUpMedias.ref,
            isRef = true
        )
        else SignUpMediaItemModel.Empty(isRef = true)

        val mediaItems =
            signUpMedias.medias.map { createSignUpMediaItem(signUpMedia = it, isRef = false) }

        items.add(refItem)
        items.addAll(mediaItems)
        if (items.size < MINIMUM_DISPLAY_ITEM_COUNT) items.addAll(List(MINIMUM_DISPLAY_ITEM_COUNT - items.size) {
            SignUpMediaItemModel.Empty(
                isRef = false
            )
        })
        else if (items.size < MAXIMUM_DISPLAY_ITEM_COUNT) items.add(SignUpMediaItemModel.Empty(isRef = false))
        return items
    }

    companion object {
        private const val MINIMUM_DISPLAY_ITEM_COUNT = 3
        private const val MAXIMUM_DISPLAY_ITEM_COUNT = 10
    }
}