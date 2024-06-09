package com.eighteen.eighteenandroid.presentation.dialog

import com.eighteen.eighteenandroid.databinding.DialogReportCompletedBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment

class ReportCompletedDialogFragment :
    BaseDialogFragment<DialogReportCompletedBinding>(DialogReportCompletedBinding::inflate) {

    override fun initView() {
        bind {
            btnReportCompleted.setOnClickListener {
                this@ReportCompletedDialogFragment.dismiss()
            }
        }
    }
}