package com.eighteen.eighteenandroid.presentation.chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.eighteen.eighteenandroid.databinding.FragmentChatRoomExitDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment

class ChatRoomExitDialogFragment :
    BaseDialogFragment<FragmentChatRoomExitDialogBinding>(FragmentChatRoomExitDialogBinding::inflate) {

    private val requestKey by lazy {
        arguments?.getString(ARGUMENT_REQUEST_KEY_KEY)
    }

    private val id by lazy {
        arguments?.getString(ARGUMENT_ID_KEY)
    }

    override fun initView() {
        bind {
            tvBtnCancel.setOnClickListener {
                requestKey?.let {
                    setFragmentResult(requestKey = it, result = bundleOf(RESULT_CANCEL_KEY to id))
                }
                dismissNow()
            }
            tvBtnConfirm.setOnClickListener {
                requestKey?.let {
                    setFragmentResult(
                        requestKey = it,
                        result = bundleOf(RESULT_CONFIRM_KEY to id)
                    )
                }
                dismissNow()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initDialogWindow()
    }

    private fun initDialogWindow() {
        dialog?.run {
            window?.run {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    companion object {
        private const val ARGUMENT_REQUEST_KEY_KEY = "ARGUMENT_REQUEST_KEY_KEY"
        private const val ARGUMENT_ID_KEY = "ARGUMENT_ID_KEY"
        private const val RESULT_CONFIRM_KEY = "RESULT_YES_KEY"
        private const val RESULT_CANCEL_KEY = "RESULT_NO_KEY"

        fun newInstance(requestKey: String, id: String) = ChatRoomExitDialogFragment().apply {
            arguments = bundleOf(
                ARGUMENT_REQUEST_KEY_KEY to requestKey,
                ARGUMENT_ID_KEY to id
            )
        }
    }

    abstract class ResultListener : FragmentResultListener {
        final override fun onFragmentResult(requestKey: String, result: Bundle) {
            result.getString(RESULT_CONFIRM_KEY)?.let {
                onConfirm(it)
            }
            result.getString(RESULT_CANCEL_KEY)?.let {
                onCancel(it)
            }
        }

        open fun onConfirm(id: String) {}

        open fun onCancel(id: String) {}
    }
}