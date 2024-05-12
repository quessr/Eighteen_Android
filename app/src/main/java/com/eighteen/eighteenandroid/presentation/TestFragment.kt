package com.eighteen.eighteenandroid.presentation

import android.os.Bundle
import com.eighteen.eighteenandroid.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding>(FragmentTestBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        bind {
            tvTest1.text = "text1"
            tvTest2.text = "text2"
        }
    }
}
