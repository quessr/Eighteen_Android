package com.eighteen.eighteenandroid.presentation.profileDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentProfileDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileDetailFragment :
    BaseFragment<FragmentProfileDetailBinding>(FragmentProfileDetailBinding::inflate) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: QuestionAnswerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun initView() {
        initNavigation()
        setupViewPagerAndTabs()
        setupQuestionAnswerList()
    }

    private fun setupQuestionAnswerList() {
        val questionAnswerItems = listOf(
            "1. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "2. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "3. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "4. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "5. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "6. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "7. Lorem ipsum dolor sit amet?" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        )
        adapter = QuestionAnswerAdapter(questionAnswerItems, binding.recyclerView)
        bind {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun setupViewPagerAndTabs() {
        val items = listOf(
            "https://cdn.seoulwire.com/news/photo/202109/450631_649892_1740.jpg",
            "https://contents-cdn.viewus.co.kr/image/2023/12/CP-2022-0017/image-de4d5a79-bbe3-4c2e-84a7-f36976345663.jpeg",
            "https://cdn.hankooki.com/news/photo/202309/107376_146623_1695826504.jpg",
            "https://cdn.dailycc.net/news/photo/202312/766253_670987_1515.png",
            "https://cdn.newsculture.press/news/photo/202306/525899_650590_620.jpg",
        )

        bind {
            viewPager.adapter = ViewPagerAdapter(items)

        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
    }

    private fun initNavigation() {
        bind {
//            btnGoMain.setOnClickListener {
//                val navController =
//                    Navigation.findNavController(requireActivity().findViewById(R.id.fragment_container_view))
//                navController.navigate(R.id.action_fragmentProfileDetail_to_fragmentMain)
        }
    }
}
