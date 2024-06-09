package com.eighteen.eighteenandroid.presentation.common

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, tag: String? = null) {
    dialogFragment.show(childFragmentManager, tag)
}