package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.eighteen.eighteenandroid.databinding.FragmentSignUpAddMediasGuideDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment

class SignUpAddMediasGuideDialogFragment :
    BaseDialogFragment<FragmentSignUpAddMediasGuideDialogBinding>(
        FragmentSignUpAddMediasGuideDialogBinding::inflate
    ) {

    override fun onResume() {
        super.onResume()
        initDialogWindow()
    }

    override fun initView() {
        //do nothing
    }

    private fun initDialogWindow() {
        dialog?.run {
            window?.run {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }
}