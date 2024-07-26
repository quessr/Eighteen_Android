package com.eighteen.eighteenandroid.presentation.myprofile.editlink

import com.eighteen.eighteenandroid.databinding.FragmentEditLinkDialogBinding
import com.eighteen.eighteenandroid.presentation.BaseDialogFragment

class EditLinkDialogFragment :
    BaseDialogFragment<FragmentEditLinkDialogBinding>(FragmentEditLinkDialogBinding::inflate) {

    override fun initView() {
        with(binding) {
//            tvTitle.text = getString(R.string.)
//            ivBtnClose.setOnClickListener {
//                dismissNow()
//            }
//            rvLinks.addItemDecoration(LinkListItemDecoration())
//            rvLinks.adapter =
//                LinkListDialogAdapter(
//                    onClickRemove = ::onClickRemove
//                ).apply {
//                    submitList(links)
//                }
        }
    }

    companion object {
        private const val ARGUMENT_REQUEST_KEY = "argument_request_key"
        private const val ARGUMENT_TITLE_KEY = "argument_title_key"
        private const val ARGUMENT_IS_EDITABLE_KEY = "argument_is_editable_key"
        private const val ARGUMENT_LINKS_KEY = "argument_links_key"

        private const val RESULT_CLOSE_KEY = "result_close_key"
        private const val RESULT_REMOVE_KEY = "result_remove_key"
        private const val RESULT_ADD_KEY = "result_add_key"
    }
}