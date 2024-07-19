package com.eighteen.eighteenandroid.presentation.profileDetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentProfileDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.MediaDetailDialogFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.MediaDetailViewModel
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailMediaModel
import kotlinx.coroutines.launch

class ProfileDetailFragment :
    BaseFragment<FragmentProfileDetailBinding>(FragmentProfileDetailBinding::inflate) {

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initNavigation()
        initMediaDetailFlow()
    }

    private fun initNavigation() {
        bind {
            btnGoMain.setOnClickListener {
                val navController =
                    Navigation.findNavController(requireActivity().findViewById(R.id.fragment_container_view))
                navController.navigate(R.id.action_fragmentProfileDetail_to_fragmentMain)
            }
        }
    }

    //TODO pageChangeCallback position 업데이트 추가
    //TODO 샘플코드 제거 미디어 모델 변환 후 전달
    private fun initMediaDetailFlow() = viewLifecycleOwner.lifecycleScope.launch {
        val video1 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val video2 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        val video3 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        val image1 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
        val image2 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
        val image3 =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg"
        val testMedias = listOf(
            MediaDetailMediaModel.Video("1", "https://picsum.photos/id/23/200/300", video1),
            MediaDetailMediaModel.Video("2", "https://picsum.photos/id/24/200/300", video2),
            MediaDetailMediaModel.Image("3", image1),
            MediaDetailMediaModel.Video("4", "https://picsum.photos/id/25/200/300", video3),
            MediaDetailMediaModel.Image("5", image2),
            MediaDetailMediaModel.Image("6", image3),
        )
        mediaDetailViewModel.setMedias(testMedias)
    }

    /**
     * 프로필 상세에서 사진/영상 클릭 시 미디어 상세화면으로 이동
     */
    private fun openMediaDetailDialogFragment() {
        showDialogFragment(MediaDetailDialogFragment())
    }
}