package com.eighteen.eighteenandroid.presentation.myprofile.editmedia

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentMyEditMediaBinding
import com.eighteen.eighteenandroid.databinding.ItemEditMediaBinding
import com.eighteen.eighteenandroid.domain.model.Media
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.common.getMimeTypeFromUri
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.common.livedata.EventObserver
import com.eighteen.eighteenandroid.presentation.common.mediapicker.MediaPickerWrapper
import com.eighteen.eighteenandroid.presentation.common.requestPermissions
import com.eighteen.eighteenandroid.presentation.common.viewModelsByBackStackEntry
import com.eighteen.eighteenandroid.presentation.editmedia.BaseEditMediaFragment
import com.eighteen.eighteenandroid.presentation.editmedia.EditMediaViewModel
import com.eighteen.eighteenandroid.presentation.editmedia.model.EditMediaResult
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.MyEditMediaEvent
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.MyEditMediaModel

class MyEditMediaFragment :
    BaseFragment<FragmentMyEditMediaBinding>(FragmentMyEditMediaBinding::inflate) {
    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val myEditMediaViewModel by viewModels<MyEditMediaViewModel>(factoryProducer = {
        //TODO profile 모델 대표이미지 구분 문의 후 적용(현재 임시로 변환)
        val medias = loginViewModel.myProfileStateFlow.value.data?.medias ?: emptyList()
        val mediaModels = medias.map {
            when (it) {
                is Media.Image -> MyEditMediaModel.Media.Image(imageUrl = it.url)
                is Media.Video -> MyEditMediaModel.Media.Video(url = it.url)
            }
        }
        val myEditMediaModel = MyEditMediaModel(repMedia = null, medias = mediaModels)
        MyEditMediaViewModel.Factory(myEditMediaModel)
    })

    private val editMediaViewModel by viewModelsByBackStackEntry<EditMediaViewModel>()

    private val mediaPickerWrapper = MediaPickerWrapper(this)

    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvBtnComplete.setOnClickListener {
                //TODO 수정 요청 api
            }
        }
        initStateFlow()
        initObserver()
    }

    private fun initStateFlow() {
        collectInLifecycle(myEditMediaViewModel.mediasStateFlow) {
            binding.glContainer.run {
                removeAllViews()
                val repView = it.repMedia?.let {
                    createMediaView(model = it, isRep = true)
                } ?: createEmptyView(isRep = true)
                addView(repView)
                it.medias.forEach {
                    val mediaView = createMediaView(model = it, isRep = false)
                    addView(mediaView)
                }
                if (it.medias.size + 1 < MAXIMUM_MEDIAS_COUNT) addView(createEmptyView(isRep = false))
                val betweenMargin = context.dp2Px(16)
                children.forEachIndexed { index, view ->
                    view.updateLayoutParams<MarginLayoutParams> {
                        val left = if (index % 2 == 1) betweenMargin / 2 else 0
                        val right = if (index % 2 == 0) betweenMargin / 2 else 0
                        setMargins(left, 0, right, betweenMargin)
                    }
                }
            }
        }
    }

    private fun createMediaView(model: MyEditMediaModel.Media, isRep: Boolean) =
        ItemEditMediaBinding.inflate(layoutInflater).apply {
            vRepContainer.isVisible = isRep
            when (model) {
                is MyEditMediaModel.Media.Image -> run {
                    if (model.imageUrl != null) {
                        ImageLoader.get().loadUrlCenterCrop(ivMedia, url = model.imageUrl)
                    } else if (model.bitmap != null) {
                        ivMedia.setImageBitmap(model.bitmap)
                    } else {
                        //TODO placeholder
                    }
                }
                is MyEditMediaModel.Media.Video -> ImageLoader.get()
                    .loadUrlCenterCrop(ivMedia, url = model.url)
            }
            clRepEmpty.isVisible = false
            ivVideoIcon.isVisible = model is MyEditMediaModel.Media.Video
            clEmpty.isVisible = false
            ivBtnDelete.setOnClickListener {
                if (isRep) myEditMediaViewModel.removeRepMedia()
                else myEditMediaViewModel.removeMedia(model)
            }
        }.root.apply {
            layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            ).apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

    private fun createEmptyView(isRep: Boolean) =
        ItemEditMediaBinding.inflate(layoutInflater).apply {
            vRepContainer.isVisible = isRep
            ivBtnDelete.isVisible = false
            ivVideoIcon.isVisible = false
            clRepEmpty.isVisible = isRep
            clEmpty.isVisible = !isRep
        }.root.apply {
            layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            ).apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            setOnClickListener {
                mediaPickerWrapper.openMediaPicker {
                    val uri = it ?: return@openMediaPicker
                    getMimeTypeFromUri(uri, context)?.let { typeString ->
                        val uriString = uri.toString()
                        editMediaViewModel.setMediaUriString(uriString = uriString)
                        val event = if (typeString.startsWith("image")) MyEditMediaEvent.Image(
                            uri = uriString,
                            isRep = isRep
                        )
                        else if (typeString.startsWith("video")) MyEditMediaEvent.Video(
                            uri = uriString,
                            isRep = isRep
                        )
                        else return@let
                        myEditMediaViewModel.setOpenEditMediaEvent(event = event)
                    }
                }
            }
        }

    private fun initObserver() {
        myEditMediaViewModel.openEditMediaEventLiveData.observe(viewLifecycleOwner, EventObserver {
            val bundle = Bundle().apply {
                putInt(
                    BaseEditMediaFragment.EDIT_MEDIA_POP_DESTINATION_ID_KEY,
                    R.id.fragmentMyEditMedia
                )
            }
            when (it) {
                is MyEditMediaEvent.Image -> {
                    findNavController().navigate(
                        R.id.fragmentEditImage,
                        bundle
                    )
                }
                is MyEditMediaEvent.Video -> {
                    val onGranted = {
                        findNavController().navigate(
                            R.id.fragmentEditVideo,
                            bundle
                        )
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        onGranted.invoke()
                    } else {
                        //TODO 권한요청 거부 시 작업(팝업 or 세팅 이동 등) 논의
                        requestPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            onGranted = onGranted
                        )
                    }
                }
            }
        })
        editMediaViewModel.editResultEventLiveData.observe(viewLifecycleOwner, EventObserver {
            val isRep = myEditMediaViewModel.openEditMediaEventLiveData.value?.peekContent()?.isRep
                ?: return@EventObserver
            val model = when (it) {
                is EditMediaResult.Image -> MyEditMediaModel.Media.Image(bitmap = it.imageBitmap)
                is EditMediaResult.Video -> MyEditMediaModel.Media.Video(url = it.uriString)
            }
            if (isRep) myEditMediaViewModel.addRepMedia(model)
            else myEditMediaViewModel.addMedia(model)
        })
    }

    companion object {
        private const val MAXIMUM_MEDIAS_COUNT = 10
    }
}