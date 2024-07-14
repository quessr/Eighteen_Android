package com.eighteen.eighteenandroid.presentation

import com.eighteen.eighteenandroid.databinding.FragmentFullWebViewBinding

class FullWebViewFragment :
    BaseFragment<FragmentFullWebViewBinding>(FragmentFullWebViewBinding::inflate) {
    override fun initView() {
        val url = arguments?.getString(URL_ARGUMENT_KEY, "") ?: ""
        binding.wvFullWebView.loadUrl(url)
    }

    companion object {
        const val URL_ARGUMENT_KEY = "url_argument_key"
    }
}