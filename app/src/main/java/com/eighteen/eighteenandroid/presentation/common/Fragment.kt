package com.eighteen.eighteenandroid.presentation.common

import android.content.Context
import android.os.Parcelable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.DialogReportSelectBinding
import com.eighteen.eighteenandroid.presentation.MainActivity

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, tag: String? = null) {
    dialogFragment.show(childFragmentManager, tag)
}

/** 신고하기 & 차단하기 선택 다이얼로그 */
fun showReportSelectDialog(context: Context, onReportClicked: () -> Unit, onBlockClicked: () -> Unit) {
    val reportSelectBinding = DialogReportSelectBinding.inflate(LayoutInflater.from(context))
    val customAlertDialog = AlertDialog.Builder(context, R.style.CustomDialog)
        .setView(reportSelectBinding.root)
        .create()

    customAlertDialog.show()
    reportSelectBinding.reportContainer.setOnClickListener {
        onReportClicked()
        customAlertDialog.dismiss()
    }

    reportSelectBinding.blockContainer.setOnClickListener {
        onBlockClicked()
        customAlertDialog.dismiss()
    }
}

/** 신고하기 & 차단하기 아이템 앵커뷰 */
fun showReportSelectDialog(itemView: View, onReportClicked: () -> Unit, onBlockClicked: () -> Unit) {
    val reportSelectDialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_report_select, null)
    val displayMetrics = itemView.context.resources.displayMetrics

    val popupWindow = PopupWindow(
        reportSelectDialogView,
        (displayMetrics.density * 145).toInt(),
        LinearLayout.LayoutParams.WRAP_CONTENT,
        true
    )

    // 팝업 메뉴 아이템에 대한 클릭 리스너 설정
    reportSelectDialogView.findViewById<LinearLayoutCompat>(R.id.report_container).setOnClickListener {
        onReportClicked()
        popupWindow.dismiss()
    }

    reportSelectDialogView.findViewById<LinearLayoutCompat>(R.id.block_container).setOnClickListener {
        onBlockClicked()
        popupWindow.dismiss()
    }

    // 팝업 뷰의 크기 측정
    reportSelectDialogView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val popupWidth = reportSelectDialogView.measuredWidth

    // X 오프셋 계산 (버튼 왼쪽에 표시)
    val xOffset = -popupWidth - itemView.width -(displayMetrics.density * 18.5).toInt()

    // Y 오프셋 계산 (버튼과 상단을 맞춤)
    val yOffset = -itemView.height

    // 팝업 창 표시
    popupWindow.showAsDropDown(itemView, xOffset, yOffset - itemView.height)

    // 팝업 창 표시
//    popupWindow.showAsDropDown(itemView)
}

fun <T : Parcelable> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T : Parcelable> Fragment.setNavigationResult(key: String, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T : Parcelable> Fragment.setNavigationResult(
    key: String,
    result: T,
    @IdRes destinationId: Int
) {
    findNavController().getBackStackEntry(destinationId).savedStateHandle[key] = result
}

fun Fragment.requestPermissions(
    vararg permissions: String,
    onGranted: (() -> Unit)? = null,
    onDenied: (() -> Unit)? = null
) {
    (activity as? MainActivity)?.permissionManager?.requestPermissions(
        permissions = permissions,
        onGranted = onGranted,
        onDenied = onDenied
    )
}


fun Fragment.hideKeyboard() {
    activity?.run {
        val inputManager =
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        inputManager?.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Fragment.clearFocus() {
    activity?.currentFocus?.clearFocus()
}

fun Fragment.hideKeyboardAndRemoveCurrentFocus() {
    hideKeyboard()
    clearFocus()
}

fun Fragment.showKeyboard(view: View) {
    activity?.run {
        val inputManager =
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        inputManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}