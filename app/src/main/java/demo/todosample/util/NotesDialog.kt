package demo.todosample.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import demo.todosample.R
import demo.todosample.entity.Todo


class NotesDialog(context: Context) : BaseDialogHelper() {
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_add_note, null)
    }
    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    val edtText: TextInputEditText by lazy {
        dialogView.findViewById<TextInputEditText>(R.id.todo_description)
    }

    val btnAdd: Button by lazy {
        dialogView.findViewById<Button>(R.id.btn_add)
    }
    val btnCancel: Button by lazy {
        dialogView.findViewById<Button>(R.id.btn_cancel)
    }

    fun cancelClickListener(func: ((Todo) -> Unit)? = null) =
            with(btnCancel) {
                setClickListenerToDialogButton(func)
            }

    fun addClickListener(func: ((Todo) -> Unit)? = null) =
            with(btnAdd) {
                setClickListenerToDialogButton(func)
            }

    private fun createTodo(): Todo {
        val description = edtText.text.toString()
        val time: Long = System.currentTimeMillis()
        return Todo(description = description, createdTime = time)
    }

    //  view click listener as extension function
    private fun View.setClickListenerToDialogButton(func: ((Todo) -> Unit)?) =
            setOnClickListener {
                func?.invoke(createTodo())
                dialog?.dismiss()
            }
}