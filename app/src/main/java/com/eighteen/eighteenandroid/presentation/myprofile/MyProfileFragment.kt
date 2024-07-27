package com.eighteen.eighteenandroid.presentation.myprofile

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.databinding.FragmentMyProfileBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment
import com.eighteen.eighteenandroid.presentation.LoginViewModel
import com.eighteen.eighteenandroid.presentation.common.showDialogFragment
import com.eighteen.eighteenandroid.presentation.myprofile.editlink.EditLinkDialogFragment
import kotlinx.coroutines.launch

class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate),
    MyProfileClickListener {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private val myProfileViewModel by viewModels<MyProfileViewModel>()

    override fun initView() {
        initStateFlow()
        binding.rvProfile.run {
            adapter = MyProfileAdapter(clickListener = this@MyProfileFragment)
            itemAnimator = null
            addItemDecoration(MyProfileItemDecoration())
        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.myProfileStateFlow.collect {
                    if (it.isSuccess()) {
                        it.data?.let { profile ->
                            myProfileViewModel.setProfile(profile)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.myProfileItemsStateFlow.collect {
                    (binding.rvProfile.adapter as? MyProfileAdapter)?.submitList(it)
                }
            }
        }
    }

    override fun onClickSetting() {
        //TODO 설정화면 이동
        Log.d("MyProfileFragment", "onClickSetting")
    }

    override fun onClickEditMedia() {
        //TODO 미디어 편집화면 이동
        Log.d("MyProfileFragment", "onClickEditMedia")
    }

    override fun onClickEditSchool() {
        //TODO 학교 편집 화면 이동
        Log.d("MyProfileFragment", "onClickEditSchool")
    }

    override fun onClickBadge() {
        //TODO 뱃지 화면 이동
        Log.d("MyProfileFragment", "onClickBadge")
    }

    override fun onClickTeen() {
        //TODO Teen 눌렀을 때
        Log.d("MyProfileFragment", "onClickTeen")
    }

    override fun onClickEditLink() {
        val editLinkDialogFragment = EditLinkDialogFragment()
        showDialogFragment(dialogFragment = editLinkDialogFragment)
    }

    //TODO 링크 직접 클릭 시 외부링크 연결?

    override fun onClickEditIntroduce() {
        //TODO 자기소개 편집
        Log.d("MyProfileFragment", "onClickEditIntroduce")
    }

    override fun onClickEditTenOfQna() {
        //TODO 10문 10답 편집
        Log.d("MyProfileFragment", "onClickEditTenOfQna")
    }

    override fun onClickExpandQna() {
        myProfileViewModel.toggleQnaExpanded()
    }
}