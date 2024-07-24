package com.eighteen.eighteenandroid.presentation.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ItemDividerBinding
import com.eighteen.eighteenandroid.databinding.ItemMainAboutTeenListviewBinding
import com.eighteen.eighteenandroid.databinding.ItemMainHeaderBinding
import com.eighteen.eighteenandroid.databinding.ItemMainHeaderMoreBinding
import com.eighteen.eighteenandroid.databinding.ItemMainPopularTeenListviewBinding
import com.eighteen.eighteenandroid.databinding.ItemMainTournamentListviewBinding
import com.eighteen.eighteenandroid.databinding.ItemTeenBinding
import com.eighteen.eighteenandroid.domain.model.User
import com.eighteen.eighteenandroid.presentation.home.adapter.diffcallback.MainItemDiffCallBack

class MainAdapter(
    private val context: Context,
    private val onTournamentMoreClicks: () -> Unit,
    private val onAboutTeenClicks: (String) -> Unit,
    private val onUserClicks: (User) -> Unit,
    private val onTournamentClicks: (Tournament) -> Unit,
    private val showUserReportSelectDialog: (User) -> Unit,
) : ListAdapter<MainItem, MainAdapter.CommonViewHolder>(MainItemDiffCallBack()) {
    companion object {
        const val HEADER = 1
        const val HEADER_WITH_MORE = 2
        const val DIVIDER = 3
        const val POPULAR_USER_LIST = 4
        const val USER_VIEW = 5
        const val ABOUT_TEEN_LIST = 6
        const val TOURNAMENT_LIST = 7
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            HEADER -> {
                val itemBinding = ItemMainHeaderBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.HeaderViewHolder(itemBinding)
            }

            HEADER_WITH_MORE -> {
                val itemBinding = ItemMainHeaderMoreBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.HeaderWithMoreViewHolder(
                    context,
                    itemBinding,
                    onTournamentMoreClicks
                )
            }

            DIVIDER -> {
                val itemBinding = ItemDividerBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.DividerViewHolder(itemBinding)
            }

            POPULAR_USER_LIST -> {
                val itemBinding = ItemMainPopularTeenListviewBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.PopularUserListViewHolder(context, itemBinding, showUserReportSelectDialog)
            }

            USER_VIEW -> {
                val itemBinding = ItemTeenBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.UserViewHolder(context, itemBinding, showUserReportSelectDialog)
            }

            ABOUT_TEEN_LIST -> {
                val itemBinding =
                    ItemMainAboutTeenListviewBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.AboutTeenListViewHolder(itemBinding)
            }

            TOURNAMENT_LIST -> {
                val itemBinding =
                    ItemMainTournamentListviewBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder.TournamentListViewHolder(itemBinding)
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        when (holder) {
            is CommonViewHolder.HeaderViewHolder -> {
                (getItem(position) as? MainItem.HeaderView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.HeaderWithMoreViewHolder -> {
                (getItem(position) as? MainItem.HeaderWithMoreView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.PopularUserListViewHolder -> {
                (getItem(position) as? MainItem.UserListView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.UserViewHolder -> {
                (getItem(position) as? MainItem.UserView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.AboutTeenListViewHolder -> {
                (getItem(position) as? MainItem.AboutTeenListView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.TournamentListViewHolder -> {
                (getItem(position) as? MainItem.TournamentListView)?.let { holder.bind(it) }
            }

            is CommonViewHolder.DividerViewHolder -> {
                (getItem(position) as? MainItem.DividerView)?.let { holder.bind(it) }
            }

            else -> throw IllegalArgumentException("Invalid ViewHolder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainItem.HeaderView -> HEADER
            is MainItem.HeaderWithMoreView -> HEADER_WITH_MORE
            is MainItem.DividerView -> DIVIDER
            is MainItem.UserListView -> POPULAR_USER_LIST
            is MainItem.AboutTeenListView -> ABOUT_TEEN_LIST
            is MainItem.TournamentListView -> TOURNAMENT_LIST
            is MainItem.UserView -> USER_VIEW
        }
    }

    sealed class CommonViewHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: MainItem)

        class HeaderViewHolder(
            private val binding: ItemMainHeaderBinding
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                val headerView = item as? MainItem.HeaderView ?: return
                with(binding) {
                    tvHeader.text = headerView.text
                }
            }
        }

        class HeaderWithMoreViewHolder(
            private val context: Context,
            private val binding: ItemMainHeaderMoreBinding,
            private val onTournamentMoreClicks: () -> Unit,
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                val headerMoreView = item as? MainItem.HeaderWithMoreView ?: return
                with(binding) {
                    tvHeader.text = headerMoreView.text

                    tvMore.setOnClickListener {
                        // 토너먼트 더 보기
                        if (context.getString(R.string.main_tournament_in_progress) == headerMoreView.text) {
                            onTournamentMoreClicks()
                        }
                    }
                }
            }
        }

        class PopularUserListViewHolder(
            private val context: Context,
            private val binding: ItemMainPopularTeenListviewBinding,
            private val showUserReportSelectDialog: (User) -> Unit,
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                val userListView = item as? MainItem.UserListView
                with(binding) {
                    val teenAdapter = TeenAdapter(context, showUserReportSelectDialog)

                    rvMainTeenPopularList.adapter = teenAdapter

                    teenAdapter.submitList(userListView?.userList)
                }
            }
        }

        class UserViewHolder(
            private val context: Context,
            private val binding: ItemTeenBinding,
            private val showUserReportSelectDialog: (User) -> Unit,
        ) : CommonViewHolder(binding) {
            @SuppressLint("SetTextI18n")
            override fun bind(item: MainItem) {
                val userView = item as? MainItem.UserView
                with(binding) {
                    userView?.let {
                        val user = it.user
                        tvSchool.text = user.userSchoolName
                        tvName.text = "${user.userName}, ${user.userAge}"
                        Glide.with(context).load(user.userImage).into(imgTodayTeen) // 프로필 이미지

                        // TODO. Button Listener
                        root.setOnClickListener {

                        }
                        btnChat.setOnClickListener {

                        }
                        btnLike.setOnClickListener {

                        }
                        btnSetting.setOnClickListener {
                            showUserReportSelectDialog(user)
                        }
                    }
                }
            }
        }

        class AboutTeenListViewHolder(
            private val binding: ItemMainAboutTeenListviewBinding
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                with(binding) {
                    val aboutTeenListView = item as? MainItem.AboutTeenListView

                    val adapter = AboutTeenAdapter()
                    adapter.submitList(aboutTeenListView?.aboutTeenList)
                    rvItemMainAboutTeenList.adapter = adapter
                }
            }
        }

        class TournamentListViewHolder(
            private val binding: ItemMainTournamentListviewBinding
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                val tournamentListView = item as? MainItem.TournamentListView
                with(binding) {
                    val tournamentAdapter = TournamentAdapter()
                    rvItemTournamentList.adapter = tournamentAdapter

                    tournamentListView?.tournamentList?.let {
                        tournamentAdapter.submitList(it)
                    }
                }
            }
        }

        class DividerViewHolder(
            private val binding: ItemDividerBinding
        ) : CommonViewHolder(binding) {
            override fun bind(item: MainItem) {
                // ...
            }
        }
    }

    fun updateView(list: List<MainItem>) {
        submitList(list)
    }
}