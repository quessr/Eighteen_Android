package com.eighteen.eighteenandroid.presentation.profileDetail

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentProfileDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.MediaDetailViewModel
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class ProfileDetailFragment :
    BaseFragment<FragmentProfileDetailBinding>(FragmentProfileDetailBinding::inflate) {

    private val mediaDetailViewModel by activityViewModels<MediaDetailViewModel>()

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
    //TODO 샘플코드 제거 미디어 모델 flow형태로 변환 후 전달
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
            MediaDetailModel.Video("1", "https://picsum.photos/id/23/200/300", video1),
            MediaDetailModel.Video("2", "https://picsum.photos/id/24/200/300", video2),
            MediaDetailModel.Image("3", image1),
            MediaDetailModel.Video("4", "https://picsum.photos/id/25/200/300", video3),
            MediaDetailModel.Image("5", image2),
            MediaDetailModel.Image("6", image3),
        )
        val testFlow = flowOf(testMedias)
        mediaDetailViewModel.setMediasFlow(testFlow)
    }

    /**
     * 프로필 상세에서 사진/영상 클릭 시 미디어 상세화면으로 이동
     */
    private fun openMediaDetailDialogFragment() {
        findNavController().navigate(R.id.action_fragmentProfileDetail_to_fragmentMediaDetail)
    }
}