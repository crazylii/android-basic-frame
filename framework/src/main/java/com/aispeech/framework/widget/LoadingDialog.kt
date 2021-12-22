package com.aispeech.framework.widget

import android.app.Dialog
import android.content.Context
import com.aispeech.framework.R
import com.aispeech.framework.databinding.DialogLoadingBinding
import com.aispeech.framework.extensions.dp2px

/**
 * Created by spq on 2021/12/6
 */
class LoadingDialog(context: Context) : BaseDialog<DialogLoadingBinding, Dialog>(context) {
    companion object {
        fun showLoading(context: Context): LoadingDialog {
            val dialog = LoadingDialog(context)
            dialog.show()
            return dialog
        }
    }

    fun hide() {
        this.dismiss()
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun createDialog(context: Context?): Dialog {
        return Dialog(context!!, R.style.DialogStyle)
    }

    override fun onInit(dialog: Dialog, binding: DialogLoadingBinding?) {
        val lp = dialog.window?.attributes
        lp?.height = mContext.dp2px(150F).toInt()
        lp?.width = mContext.dp2px(130F).toInt()
        dialog.window?.attributes = lp
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onDialogDismiss(dialog: Dialog?) {

    }

    override fun onDialogShow(dialog: Dialog?) {

    }
}
