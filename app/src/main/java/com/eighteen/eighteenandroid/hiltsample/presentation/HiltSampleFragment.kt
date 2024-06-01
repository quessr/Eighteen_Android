package com.eighteen.eighteenandroid.hiltsample.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eighteen.eighteenandroid.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HiltSampleFragment : Fragment(R.layout.fragment_hilt_sample) {
    private val hiltSampleViewModel by viewModels<HiltSampleViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = view.findViewById<TextView>(R.id.tv_hilt_sample_test).apply {
            setOnClickListener {
                hiltSampleViewModel.fetchTestData()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hiltSampleViewModel.textStateFlow.collect {
                    tv.text = it.firstOrNull()?.toString()
                }
            }
        }

    }
}