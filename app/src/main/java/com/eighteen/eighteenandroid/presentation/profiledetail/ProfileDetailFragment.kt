package com.eighteen.eighteenandroid.presentation.profiledetail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentProfileDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.MediaDetailDialogFragment
import com.eighteen.eighteenandroid.presentation.mediadetail.MediaDetailViewModel
import com.eighteen.eighteenandroid.presentation.mediadetail.model.MediaDetailMediaModel
import com.eighteen.eighteenandroid.presentation.profiledetail.model.ProfileDetailModel
import com.eighteen.eighteenandroid.presentation.profiledetail.viewholder.ProfileDetailViewHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileDetailFragment() :
    BaseFragment<FragmentProfileDetailBinding>(FragmentProfileDetailBinding::inflate) {

    private val mediaDetailViewModel by viewModels<MediaDetailViewModel>()
    private val profileDetailViewModel by viewModels<ProfileDetailViewModel>()

    private lateinit var mediaItems: List<ProfileDetailModel.MediaItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerViewScrollListener()
        initViewModel()
        setupInitialData()
        observeMediaItems()
    }

    override fun initView() {
        initNavigation()

        bind {
            ivMore.setOnClickListener {
//                context?.let {
//                    showReportSelectDialog(it) {
//                        showDialogFragment(ReportDialogFragment())
//                    }
//                }
            }
            clLike.setOnClickListener {
                profileDetailViewModel.toggleLike()
            }
            ivSound.setOnClickListener {
                mediaDetailViewModel.toggleVolume()
            }
        }
    }

    private fun observeMediaItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileDetailViewModel.mediaItems.collectLatest { mediaItems ->
                    val hasVideo = mediaItems.any { it.isVideo }
                    Log.d("ProfileDetailFragment", "hasVideo : $hasVideo")
                    binding.ivSound.isVisible = hasVideo
                }
            }
        }
    }

    private fun setupAdapter() {
        val onPageChangeCallbackForVisibilitySoundIcon =
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val mediaItems = profileDetailViewModel.mediaItems.value
                    if (mediaItems.isNotEmpty() && position < mediaItems.size) {
                        val mediaItem = mediaItems[position]
                        binding.ivSound.isVisible = mediaItem.isVideo
                    }
                }
            }


        bind {
            profileDetailRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            profileDetailRecyclerview.adapter =
                ProfileDetailAdapter(
                    profileDetailViewModel,
                    viewLifecycleOwner,
                    onPageChangeCallbackForVisibilitySoundIcon,
                    onPageChangeCallbackForImagePosition = { currentPosition ->
                        profileDetailViewModel.setCurrentPosition(currentPosition)
                    }, onLikeChangeCallback = { profileDetailViewModel.toggleLike() })
            profileDetailRecyclerview.itemAnimator = null
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileDetailViewModel.items.collectLatest { details ->
                    (binding.profileDetailRecyclerview.adapter as? ProfileDetailAdapter)?.submitList(
                        details
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileDetailViewModel.isLike.collect { isLiked ->
                    binding.ivHeart.setImageResource(
                        if (isLiked) R.drawable.ic_full_heart else R.drawable.ic_empty_heart
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mediaDetailViewModel.mediaDetailStateFlow.collect {
                    setVolume(isVolumeOn = it.isVolumeOn)
                }
            }
        }
    }

    private fun setupRecyclerViewScrollListener() {
        bind {
            profileDetailRecyclerview.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val viewPagerPosition = findViewPagerPosition()
                    val viewHolder =
                        recyclerView.findViewHolderForAdapterPosition(viewPagerPosition) as? ProfileDetailViewHolder.Images

                    if (viewHolder != null) {
                        val viewPagerBottom = viewHolder.itemView.bottom
                        val headerBottom = binding.header.bottom
                        if (headerBottom <= viewPagerBottom) {
                            binding.header.setBackgroundColor(Color.TRANSPARENT)
                            binding.ivClose.setColorFilter(Color.WHITE)
                            binding.ivMore.setColorFilter(Color.WHITE)
                            binding.ivSound.setColorFilter(Color.WHITE)
                        } else {
                            binding.header.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.background_color
                                )
                            )
                            binding.ivClose.setColorFilter(Color.BLACK)
                            binding.ivMore.setColorFilter(Color.BLACK)
                            binding.ivSound.setColorFilter(Color.BLACK)
                        }
                    }
                }
            })
        }
    }

    private fun findViewPagerPosition(): Int {
        // ViewPager2가 포함된 아이템의 위치를 반환
        return profileDetailViewModel.items.value?.indexOfFirst { it is ProfileDetailModel.ProfileImages }
            ?: -1
    }

    private fun setVolume(isVolumeOn: Boolean) {
        val drawableRes =
            if (isVolumeOn) R.drawable.ic_unmute else R.drawable.ic_mute
        binding.ivSound.setImageResource(drawableRes)
    }

    private fun setupInitialData() {
        val qnaList = List(10) { index ->
            ProfileDetailModel.Qna(
                id = index.toString(),
                question = "${index + 1}. Lorem ipsum dolor sit amet?",
                answer = "${index + 1}. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        }

        // 비디오인지 여부를 결정하는 함수
        fun determineIfMedia(link: String): Boolean {
            val videoExtensions = listOf("mp4", "avi", "mkv", "mov")
            return videoExtensions.any { link.endsWith(it, ignoreCase = true) }
        }

        val mediaLinks = listOf(
            "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
//            "https://contents-cdn.viewus.co.kr/image/2023/12/CP-2022-0017/image-de4d5a79-bbe3-4c2e-84a7-f36976345663.jpeg",
//            "https://cdn.hankooki.com/news/photo/202309/107376_146623_1695826504.jpg",
            "https://cdn.dailycc.net/news/photo/202312/766253_670987_1515.png",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "https://cdn.newsculture.press/news/photo/202306/525899_650590_620.jpg",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        )

        mediaItems = mediaLinks.map { link ->
            val isVideo = determineIfMedia(link)

            ProfileDetailModel.MediaItem(
                url = link,
                isVideo = isVideo
            )
        }

        val initialData = listOf(
            ProfileDetailModel.ProfileImages(
                id = "2",
                mediaItems = mediaItems,
                likeCount = 100
            ),
            ProfileDetailModel.ProfileInfo(
                id = "1",
                name = "김 에스더",
                age = 16,
                school = "서울 중학교",
            ),
            ProfileDetailModel.BadgeAndTeen(
                id = "2",
                badgeCount = 10,
                teenAward = "5월 2주차 우승",
            ),
            ProfileDetailModel.Introduction(
                id = "4",
                personalityType = "INTP",
                introductionText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),

            ProfileDetailModel.QnaListTitle(id = "5"),
            ProfileDetailModel.QnaToggle(id = "6", isExpanded = true)
        )
        profileDetailViewModel.setItems(initialData)
        profileDetailViewModel.setMediaItems(mediaItems)
        profileDetailViewModel.updateQnaItems(qnaList)
        initMediaDetailFlow()
    }

    private fun initNavigation() {
        bind {
            ivClose.setOnClickListener {
                val navController = findNavController()
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
            MediaDetailMediaModel.Video("1", video1),
            MediaDetailMediaModel.Video("2", video2),
            MediaDetailMediaModel.Image("3", image1),
            MediaDetailMediaModel.Video("4", video3),
            MediaDetailMediaModel.Image("5", image2),
            MediaDetailMediaModel.Image("6", image3),
        )
        mediaDetailViewModel.setMedias(medias = testMedias)
    }

    /**
     * 프로필 상세에서 사진/영상 클릭 시 미디어 상세화면으로 이동
     */
    private fun openMediaDetailDialogFragment() {
        showDialogFragment(MediaDetailDialogFragment())
    }

    companion object {
        private const val CURRENT_POSITION_KEY = "CURRENT_POSITION_KEY"
    }
}
