package com.eighteen.eighteenandroid.presentation.myprofile.setting

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.databinding.FragmentSettingBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

//TODO 버튼 클릭 기능 추가
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    override fun initView() {
        bind {

            ivBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            ivBtnManagingInfo.setOnClickListener {
                //TODO 내 정보 관리
                Log.d("SettingFragment", "내 정보 관리")
            }

        }
    }
}