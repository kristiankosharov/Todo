package demo.todosample.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


/*
 * Notes Dialog
 */
inline fun Activity.showNotesAlertDialog(func: NotesDialog.() -> Unit): AlertDialog =
        NotesDialog(this).apply {
            func()
        }.create()

inline fun Fragment.showNotesAlertDialog(func: NotesDialog.() -> Unit): AlertDialog =
        NotesDialog(this.context!!).apply {
            func()
        }.create()
