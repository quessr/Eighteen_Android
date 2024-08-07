package com.eighteen.eighteenandroid.presentation.home

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.common.enums.Tag
import com.eighteen.eighteenandroid.databinding.FragmentMainBinding
import com.eighteen.eighteenandroid.domain.model.AboutTeen
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.common.createChip
import com.eighteen.eighteenandroid.presentation.common.setTagStyle
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.common.showReportSelectDialog
import com.eighteen.eighteenandroid.presentation.dialog.ReportDialogFragment
import com.eighteen.eighteenandroid.presentation.home.adapter.MainAdapter
import com.eighteen.eighteenandroid.presentation.home.adapter.MainAdapterListener
import com.eighteen.eighteenandroid.presentation.home.adapter.MainItem
import com.eighteen.eighteenandroid.presentation.home.adapter.Tournament
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

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

    override fun initView() {
        initChipGroup()
        initMain()
    }

    private fun initMainAdapter() {
        initMainAdapterListener()

        mainAdapter = MainAdapter(
            context = requireContext(),
            listener = mainAdapterListener
        )

        bind {
            with(rvMain) {
                adapter = mainAdapter
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
                findNavController().navigate(R.id.action_fragmentMain_to_fragmentProfileDetail)   // 유저 상세로 이동
            }

            /**
             * 유저 좋아요 클릭
             */
            override fun onUserLikeClicks(user: User) {
                // TODO. User Like API 호출
            }

            /**
             * 유저 채팅 버튼 클릭
             */
            override fun onUserChatClicks(user: User) {
//                findNavController().navigate(R.id.action_fragmentMain_to_fragmentChat)
            }

            /**
             * 유저 더보기 클릭 -> 신고, 차단 다이얼로그 보여지기
             */
            override fun onUserMoreClicks(itemView: View, user: User) {
                // TODO. 차단이나 신고에 의한 UserID 필요
                showReportSelectDialog(
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
                val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

                when(title) {
                    "Teen" -> bottomNavigationView.selectedItemId = R.id.btn_bottom_teen
                    "채팅" -> bottomNavigationView.selectedItemId = R.id.btn_bottom_chat
                    "토너먼트" -> {}
                    "나만의 Teen" -> bottomNavigationView.selectedItemId = R.id.fragmentMyProfile
                }
            }

            /**
             * 토너먼트 클릭
             */
            override fun onTournamentClicks(tournament: Tournament) {
                when(tournament) {
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
            override fun scrollToPreviousUser(recyclerView: RecyclerView) {
                recyclerView.smoothScrollToPosition(viewModel.savedPosition)
            }

            /**
             * 인기 Teen 현재 보고있는 유저 위치 저장
             */
            override fun saveUserPosition(position: Int) {
                viewModel.savedPosition = position
            }
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
                        AboutTeen("채팅","채팅을 통해 친구들과 소통해보세요!"),
                        AboutTeen("나만의 Teen","나만의 프로필을 등록해보세요!")
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
                        AboutTeen("채팅","채팅을 통해 친구들과 소통해보세요!"),
                        AboutTeen("나만의 Teen","나만의 프로필을 등록해보세요!")
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
}