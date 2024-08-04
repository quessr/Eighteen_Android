package com.eighteen.eighteenandroid.presentation.dialog

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.eighteen.eighteenandroid.databinding.DialogDatePickerBinding
import com.eighteen.eighteenandroid.presentation.BaseBottomSheetDialogFragment
import com.eighteen.eighteenandroid.presentation.common.getParcelableOrNull
import java.util.Calendar


class DatePickerDialogFragment :
    BaseBottomSheetDialogFragment<DialogDatePickerBinding>(DialogDatePickerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as? View)?.run {
            backgroundTintMode = PorterDuff.Mode.CLEAR
            backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun initView() {
        bind {
            tvTitle.text = arguments?.getString(ARGUMENT_TITLE_KEY)
            tvBtnConfirm.setOnClickListener {
                val (year, month, day) = dpPicker.run {
                    Triple(year, month, dayOfMonth)
                }
                val bundle = bundleOf(DATE_RESULT_KEY to Calendar.getInstance().apply {
                    set(year, month, day)
                })
                val requestKey = arguments?.getString(ARGUMENT_REQUEST_KEY_KEY) ?: ""
                setFragmentResult(requestKey, bundle)
                dismiss()
            }
        }
    }

    companion object {
        private const val DATE_RESULT_KEY = "date_result_key"
        private const val ARGUMENT_REQUEST_KEY_KEY = "argument_request_key_key"
        private const val ARGUMENT_TITLE_KEY = "argument_title_key"
        fun newInstance(requestKey: String, title: String = "") = DatePickerDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_REQUEST_KEY_KEY, requestKey)
                putString(ARGUMENT_TITLE_KEY, title)
            }
        }
    }

    abstract class DatePickerResultListener : FragmentResultListener {
        final override fun onFragmentResult(requestKey: String, result: Bundle) {
            result.getParcelableOrNull(DATE_RESULT_KEY, Calendar::class.java)?.let {
                onConfirmResult(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH) + 1,
                    it.get(Calendar.DATE)
                )
            }
        }

        abstract fun onConfirmResult(year: Int, month: Int, day: Int)
    }
}