package demo.todosample.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class BaseDialogHelper {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = true
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null

    //  dialog create
    open fun create(): AlertDialog {
        dialog = builder
                .setCancelable(cancelable)
                .create()

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }
}