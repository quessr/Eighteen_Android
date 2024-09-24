package com.eighteen.eighteenandroid.presentation.myprofile.editmedia

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentEditMediaBinding
import com.eighteen.eighteenandroid.databinding.ItemEditMediaBinding
import com.eighteen.eighteenandroid.domain.model.Media
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.collectInLifecycle
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import com.eighteen.eighteenandroid.presentation.common.imageloader.ImageLoader
import com.eighteen.eighteenandroid.presentation.myprofile.editmedia.model.EditMediaModel

class EditMediaFragment :
    BaseFragment<FragmentEditMediaBinding>(FragmentEditMediaBinding::inflate) {
    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val editMediaViewModel by viewModels<EditMediaViewModel>(factoryProducer = {
        //TODO profile 모델 대표이미지 구분 문의 후 적용(현재 임시로 변환)
        val medias = loginViewModel.myProfileStateFlow.value.data?.medias ?: emptyList()
        val mediaModels = medias.map {
            when (it) {
                is Media.Image -> EditMediaModel.Media.Image(imageUrl = it.url)
                is Media.Video -> EditMediaModel.Media.Video(url = it.url)
            }
        }
        val editMediaModel = EditMediaModel(repMedia = null, medias = mediaModels)
        EditMediaViewModel.Factory(editMediaModel)
    })

    override fun initView() {
        bind {
            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        initStateFlow()
    }

    private fun initStateFlow() {
        collectInLifecycle(editMediaViewModel.mediasStateFlow) {
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

    private fun createMediaView(model: EditMediaModel.Media, isRep: Boolean) =
        ItemEditMediaBinding.inflate(layoutInflater).apply {
            vRepContainer.isVisible = isRep
            when (model) {
                is EditMediaModel.Media.Image -> run {
                    if (model.imageUrl != null) {
                        ImageLoader.get().loadUrlCenterCrop(ivMedia, url = model.imageUrl)
                    } else if (model.bitmap != null) {
                        ivMedia.setImageBitmap(model.bitmap)
                    } else {
                        //TODO placeholder
                    }
                }
                is EditMediaModel.Media.Video -> ImageLoader.get()
                    .loadUrlCenterCrop(ivMedia, url = model.url)
            }
            clRepEmpty.isVisible = false
            ivVideoIcon.isVisible = model is EditMediaModel.Media.Video
            clEmpty.isVisible = false
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
        }

    companion object {
        private const val MAXIMUM_MEDIAS_COUNT = 10
    }
}