package com.eighteen.eighteenandroid.presentation.profileDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentProfileDetailBinding
import com.eighteen.eighteenandroid.presentation.BaseFragment

class ProfileDetailFragment : BaseFragment<FragmentProfileDetailBinding>(FragmentProfileDetailBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initNavigation()
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
}