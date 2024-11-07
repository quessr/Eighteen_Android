package com.eighteen.eighteenandroid.presentation.home

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.eighteen.eighteenandroid.domain.model.AboutTeen
import com.eighteen.eighteenandroid.domain.model.MainItem
import com.eighteen.eighteenandroid.domain.model.Tournament
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.createChip
import com.eighteen.eighteenandroid.presentation.common.findViewHolderOrNull
import com.eighteen.eighteenandroid.presentation.common.setTagStyle
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.common.showReportSelectDialogLeft
import com.eighteen.eighteenandroid.presentation.common.throttleClick
import com.eighteen.eighteenandroid.presentation.dialog.ReportDialogFragment
import com.eighteen.eighteenandroid.presentation.home.adapter.MainAdapter
import com.eighteen.eighteenandroid.presentation.home.adapter.MainAdapterListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @file MainFragment.kt
 * @date 05/08/2024
 * 메인화면
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel by viewModels<MainViewModel>()

    private var selectedChip: Chip? = null
    private lateinit var mainAdapter: MainAdapter

    private var userList = listOf<User>()
    private var aboutTeenList = listOf<AboutTeen>()
    private var tournamentList = listOf<Tournament>()

    private lateinit var mainAdapterListener: MainAdapterListener

    val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 200 }
    val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 200 }
    var isTop = true

    private var autoScrollJob: Job? = null
    private var isAutoScrolling = false

    override fun initView() {
        initChipGroup()
        initMain()
    }

    private fun getCenterItemPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        // RecyclerView의 중앙 Y 위치 계산
        val centerY = recyclerView.height / 2

        // 현재 화면에 보이는 첫 번째 아이템과 마지막 아이템의 포지션
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        var closestPosition = -1
        var closestDistance = Int.MAX_VALUE

        // 현재 화면에 보이는 아이템들 중 중앙에 가장 가까운 아이템 찾기
        for (i in firstVisiblePosition..lastVisiblePosition) {
            val itemView = layoutManager.findViewByPosition(i)

            // 아이템 뷰의 중앙 Y 위치 계산
            val itemCenterY = (itemView!!.top + itemView.bottom) / 2

            // 아이템 뷰의 중앙 위치와 RecyclerView의 중앙 위치 간의 거리 계산
            val distance = kotlin.math.abs(itemCenterY - centerY)

            // 가장 가까운 아이템을 찾음
            if (distance < closestDistance) {
                closestDistance = distance
                closestPosition = i
            }
        }

        return closestPosition
    }

    private fun initMainAdapter() {
        initMainAdapterListener()

        mainAdapter =
            MainAdapter(context = requireContext(), listener = mainAdapterListener).apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY // 현재 스크롤 위치 저장
            }

        bind {
            with(rvMain) {
                adapter = mainAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        // 스크롤이 위로 되면 (dy < 0) 버튼 숨기기, 아래로 스크롤 시( dy > 0 ) 버튼 보여주기
                        if (dy > 0 && btnScrollTop.visibility == View.GONE) {
                            btnScrollTop.startAnimation(fadeIn)
                            btnScrollTop.visibility = View.VISIBLE
                        }
                    }

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (!canScrollVertically(-1)) {
                            isTop = true
                        } else {
                            isTop = false
                        }

                        val layoutManager = (layoutManager as? LinearLayoutManager)

//                        Log.i("MainScrollStateChanged", "findLastVisible = ${layoutManager?.findLastVisibleItemPosition().toString()}")
//                        Log.i("MainScrollStateChanged", "findFirstVisible = ${layoutManager?.findFirstVisibleItemPosition().toString()}")
//                        Log.i("MainScrollStateChanged", "findLastCompletely = ${layoutManager?.findLastCompletelyVisibleItemPosition()}")
//                        Log.i("MainScrollStateChanged", "findFirstCompletely = ${layoutManager?.findFirstCompletelyVisibleItemPosition()}")

                        val firstVisiblePosition = layoutManager?.findFirstVisibleItemPosition()
                        if (firstVisiblePosition != null) {
                            if (firstVisiblePosition > 1) {
                                mainAdapterListener.stopAutoScroll()
                            }
                        }

                        when (newState) {
                            RecyclerView.SCROLL_STATE_IDLE -> {
                                viewModel.pageScrollPosition = getCenterItemPosition(recyclerView)

                                if (isTop && btnScrollTop.isVisible) {
                                    btnScrollTop.startAnimation(fadeOut)
                                    btnScrollTop.visibility = View.GONE
                                } else {
                                    if (!btnScrollTop.isVisible) {
                                        btnScrollTop.visibility = View.VISIBLE
                                        btnScrollTop.startAnimation(fadeIn)
                                    }
                                }
                            }

                            else -> {}
                        }
                    }
                })
            }

            // Top 버튼
            btnScrollTop.throttleClick(viewLifecycleOwner.lifecycleScope) {
                appbarLayout.setExpanded(true, true)
                rvMain.smoothScrollToPosition(0)
            }
        }

//        livedata.observe() {
        // page1 -> 10개

        // current
        // 전체 리스트를 주진 않고

//            current + 10
//        }

        // 마지막 아이템을 만나면

        // 데이터 함수 호출

        // 라이브데이터 값 바꾸고

        // 뷰 갱신
    }

    private fun initMainAdapterListener() {
        mainAdapterListener = object : MainAdapterListener {
            /**
             * 유저 클릭, 상세로 이동
             */
            override fun onUserClicks(user: User) {
                stopAutoScroll()
                findNavController().navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)   // 유저 상세로 이동
            }

            /**
             * 유저 좋아요 클릭
             */
            override fun onUserLikeClicks(user: User) {
                stopAutoScroll()
                // TODO. User Like API 호출
            }

            /**
             * 유저 채팅 버튼 클릭
             */
            override fun onUserChatClicks(user: User) {
                stopAutoScroll()
//                findNavController().navigate(R.id.action_fragmentMain_to_fragmentChat)
            }

            /**
             * 유저 더보기 클릭 -> 신고, 차단 다이얼로그 보여지기
             */
            override fun onUserMoreClicks(itemView: View, user: User) {
                stopAutoScroll()
                // TODO. 차단이나 신고에 의한 UserID 필요
                showReportSelectDialogLeft(
                    itemView,
                    onReportClicked = {
                        showDialogFragment(ReportDialogFragment.newInstance(user))
                    },
                    onBlockClicked = {}
                )
            }

            /**
             * About Teen 테마 선택
             */
            override fun onAboutTeenClicks(title: String) {
                val bottomNavigationView =
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

                when(title) {
                    "Teen" -> bottomNavigationView.selectedItemId = R.id.teenMainFragment
                    "채팅" -> bottomNavigationView.selectedItemId = R.id.fragmentChat
                    "토너먼트" -> {}
                    "나만의 Teen" -> bottomNavigationView.selectedItemId = R.id.fragmentMyProfile
                }
            }

            /**
             * 토너먼트 클릭
             */
            override fun onTournamentClicks(tournament: Tournament) {
                when (tournament) {
                    is Tournament.Exercise -> {}
                    is Tournament.Study -> {}
                }
            }

            /**
             * 토너먼트 더보기 클릭
             */
            override fun onTournamentMoreClicks() {
//                findNavController().navigate(R.id.action_fragmentMain_to_fragmentTournament)
            }

            /**
             * 이전에 보던 인기 Teen 유저로 이동
             */
            override fun scrollToPreviousUser() {
                val popularUserListViewHolder =
                    binding.rvMain.findViewHolderOrNull<MainAdapter.CommonViewHolder.PopularUserListViewHolder>()
                val rvPopularUserList = popularUserListViewHolder?.binding?.rvMainTeenPopularList

                val layoutManager = rvPopularUserList?.layoutManager as? LinearLayoutManager
                layoutManager?.scrollToPositionWithOffset(viewModel.popularUserPosition, 0)

                val targetView = layoutManager?.findViewByPosition(viewModel.popularUserPosition)
                targetView?.let {
                    val offset = rvPopularUserList.width / 2 - it.width / 2
                    layoutManager.scrollToPositionWithOffset(viewModel.popularUserPosition, offset)
                }

                stopAutoScroll()
                startAutoScroll()
            }

            /**
             * 인기 Teen 현재 보고있는 유저 위치 저장
             */
            override fun saveUserPosition(position: Int) {
                viewModel.popularUserPosition = position
            }

            override fun saveScrollPosition(position: Int) {
                viewModel.pageScrollPosition = position
            }

            override fun startAutoScroll() {
                isAutoScrolling = true
                autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
                    val smoothScroller = linearSmoothScroller(requireContext())

                    while (isAutoScrolling) {
                        delay(4500)

                        val popularUserListViewHolder =
                            binding.rvMain.findViewHolderOrNull<MainAdapter.CommonViewHolder.PopularUserListViewHolder>()

                        val rvPopularUserList =
                            popularUserListViewHolder?.binding?.rvMainTeenPopularList
                        val itemCount = rvPopularUserList?.adapter?.itemCount ?: 0

                        if (itemCount > 0) {
                            viewModel.popularUserPosition =
                                (viewModel.popularUserPosition + 1) % itemCount
                            smoothScroller.targetPosition = viewModel.popularUserPosition
                            rvPopularUserList?.layoutManager?.startSmoothScroll(smoothScroller)
                        }
                    }
                }
            }

            override fun stopAutoScroll() {
                isAutoScrolling = false
                autoScrollJob?.cancel()
            }
        }
    }

    private fun linearSmoothScroller(context: Context) = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }

        // 스크롤 속도: 80f
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return 80f / displayMetrics.densityDpi
        }
    }

    private fun initMain() {
        initMainAdapter()
        initData()
        initUserListObserver()
    }

    private fun initUserListObserver() {
//        viewModel.mainItems.observe(viewLifecycleOwner) {
//            mainAdapter.updateView(it)
//        }

        viewModel.userData.observe(viewLifecycleOwner) {
            userList = it
            updateMain()
        }
    }

    private fun updateMain() {
        mainAdapter.updateView(
            listOf(
                MainItem.HeaderView(resources.getString(R.string.main_today_teen)),
                MainItem.UserListView(
                    userList
                ), // User List
                MainItem.DividerView,
                MainItem.HeaderView(getString(R.string.main_about_teen)),
                MainItem.AboutTeenListView(
//                    aboutTeenList
                    listOf(
                        AboutTeen("Teen", "친구들의 프로필을 투표해보세요!"),
                        AboutTeen("토너먼트", "투표 결과를 한 눈에 볼 수 있어요!"),
                        AboutTeen("채팅", "채팅을 통해 친구들과 소통해보세요!"),
                        AboutTeen("나만의 Teen", "나만의 프로필을 등록해보세요!")
                    )
                ), // About Teen List
                MainItem.DividerView,
                MainItem.HeaderWithMoreView(getString(R.string.main_tournament_in_progress)),
                MainItem.TournamentListView(
//                    tournamentList
                    listOf(
                        Tournament.Exercise,
                        Tournament.Study
                    )
                ), // Tournament List
                MainItem.DividerView,
                MainItem.HeaderView(getString(R.string.main_another_teen)),
                MainItem.UserView(
                    User(
                        userImage = "https://image.blip.kr/v1/file/021ec61ff1c9936943383b84236a0e69",
                        userId = "1",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                ),
                MainItem.UserView(
                    User(
                        userImage = "https://cdn.newsculture.press/news/photo/202308/529742_657577_5726.jpg",
                        userId = "2",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                ),
                MainItem.UserView(
                    User(
                        userImage = "https://mblogthumb-phinf.pstatic.net/MjAyMTEwMzFfMTY1/MDAxNjM1NjUzMTI2NjI3.xXYQteLLoWLKcR9YnXS0Hk_y-DInauMzF25g7FxlcScg.2Y-neBBMVoP2IhcwzX2Zy2HB2d8EnM_cY76FVLuk_1Yg.JPEG.ssun2415/IMG_4148.jpg?type=w800",
                        userId = "3",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                )
            )
        )
    }

    private fun initData() {
        mainAdapter.updateView(
            listOf(
                MainItem.UserListView(
                    emptyList()
                ), // User List
                MainItem.DividerView,
                MainItem.HeaderView(getString(R.string.main_about_teen)),
                MainItem.AboutTeenListView(
                    listOf(
                        AboutTeen("Teen", "친구들의 프로필을 투표해보세요!"),
                        AboutTeen("토너먼트", "투표 결과를 한 눈에 볼 수 있어요!"),
                        AboutTeen("채팅", "채팅을 통해 친구들과 소통해보세요!"),
                        AboutTeen("나만의 Teen", "나만의 프로필을 등록해보세요!")
                    )
                ), // About Teen List
                MainItem.DividerView,
                MainItem.HeaderWithMoreView(getString(R.string.main_tournament_in_progress)),
                MainItem.TournamentListView(
                    listOf(
                        Tournament.Exercise,
                        Tournament.Study
                    )
                ), // Tournament List
                MainItem.DividerView,
                MainItem.HeaderView(getString(R.string.main_another_teen)),
                MainItem.UserView(
                    User(
                        userImage = "https://image.blip.kr/v1/file/021ec61ff1c9936943383b84236a0e69",
                        userId = "1",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                ),
                MainItem.UserView(
                    User(
                        userImage = "https://cdn.newsculture.press/news/photo/202308/529742_657577_5726.jpg",
                        userId = "2",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                ),
                MainItem.UserView(
                    User(
                        userImage = "https://mblogthumb-phinf.pstatic.net/MjAyMTEwMzFfMTY1/MDAxNjM1NjUzMTI2NjI3.xXYQteLLoWLKcR9YnXS0Hk_y-DInauMzF25g7FxlcScg.2Y-neBBMVoP2IhcwzX2Zy2HB2d8EnM_cY76FVLuk_1Yg.JPEG.ssun2415/IMG_4148.jpg?type=w800",
                        userId = "3",
                        userName = "김 에스더",
                        userAge = "16",
                        userSchoolName = "서울 중학교",
                        tag = "운동"
                    )
                )
            )
        )
    }

    private fun initChipGroup() {
        for (tag in Tag.values()) {
            val chip = createChip(requireContext(), tag.strValue)
            if (tag == Tag.ALL) { // 화면 최초 진입 시 전체 태그가 클릭된 상태여야함
                chip.setTagStyle(isBlackBackground = true)
                selectedChip = chip
            }
            chip.setOnClickListener { _ ->
                selectedChip?.setTagStyle(isBlackBackground = false)
                chip.setTagStyle(isBlackBackground = true)
                selectedChip = chip
            }
            bind {
                chipGroup.addView(chip)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(300)
//            bind {
//                // 마지막 스크롤 상태로 돌아오기
//                if (viewModel.pageScrollPosition != -1) {
//
//                    rvMain.scrollToPosition(viewModel.pageScrollPosition)
//
//                    // LayoutManager에서 해당 위치의 아이템을 중앙에 위치시키도록 오프셋 조정
//                    rvMain.post {
//                        val layoutManager = rvMain.layoutManager as LinearLayoutManager
//                        val viewAtPosition = layoutManager.findViewByPosition(viewModel.pageScrollPosition)
//                        if (viewAtPosition != null) {
//                            val offset = (rvMain.height - viewAtPosition.height) / 2
//                            layoutManager.scrollToPositionWithOffset(viewModel.pageScrollPosition, offset)
//                        }
//                    }
//                }
//            }
        }
    }
}