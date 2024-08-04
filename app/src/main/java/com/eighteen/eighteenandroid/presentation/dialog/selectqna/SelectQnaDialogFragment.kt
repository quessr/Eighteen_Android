package com.eighteen.eighteenandroid.presentation.dialog.selectqna

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.eighteen.eighteenandroid.databinding.FragmentSelectQnaDialogBinding
import com.eighteen.eighteenandroid.domain.model.QnaType
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment

class SelectQnaDialogFragment :
    BaseDialogFragment<FragmentSelectQnaDialogBinding>(FragmentSelectQnaDialogBinding::inflate) {

    private val requestKey by lazy {
        arguments?.getString(ARGUMENT_REQUEST_KEY)
    }

    private val title by lazy {
        arguments?.getString(ARGUMENT_TITLE_KEY, "") ?: ""
    }

    private val qnas by lazy {
        arguments?.getStringArray(ARGUMENT_QNAS_KEY)?.map { QnaType.valueOf(it) } ?: emptyList()
    }

    override fun initView() {
        bind {
            tvTitle.text = title
            rvQnas.adapter = SelectQnaDialogAdapter(onClickItem = ::onClickItem).apply {
                submitList(qnas)
            }
            rvQnas.addItemDecoration(SelectQnaDialogItemDecoration())
        }
    }

    private fun onClickItem(qnaType: QnaType) {
        requestKey?.let {
            val result = bundleOf(RESULT_QNA_TYPE_KEY to qnaType.name)
            setFragmentResult(requestKey = it, result = result)
            dismissNow()
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
        private const val ARGUMENT_REQUEST_KEY = "argument_request_key"
        private const val ARGUMENT_TITLE_KEY = "argument_title_key"
        private const val ARGUMENT_QNAS_KEY = "argumenet_qnas_key"

        private const val RESULT_QNA_TYPE_KEY = "result_qna_type_key"

        fun newInstance(requestKey: String, title: String = "", qnas: List<QnaType>) =
            SelectQnaDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_REQUEST_KEY, requestKey)
                    putString(ARGUMENT_TITLE_KEY, title)
                    putStringArray(ARGUMENT_QNAS_KEY, qnas.map { it.name }.toTypedArray())
                }
            }
    }


    abstract class SelectQnaResultListener : FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            result.getString(RESULT_QNA_TYPE_KEY)?.let { onSelectResult(QnaType.valueOf(it)) }
        }

        open fun onSelectResult(qnaType: QnaType) {}
    }

}