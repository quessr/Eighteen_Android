package com.eighteen.eighteenandroid.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding>(FragmentTestBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun initView() {
        bind {
            binding.tvTest2
            binding.tvTest1
        }
    }
}
