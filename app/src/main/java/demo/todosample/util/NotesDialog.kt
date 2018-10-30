package demo.todosample.util

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import demo.todosample.R
import demo.todosample.databinding.DialogAddNoteBinding
import demo.todosample.entity.Todo


class NotesDialog : AppCompatDialogFragment() {

    companion object TAG {
        fun getTag() = NotesDialog::class.simpleName.toString()
        const val BUNDLE_DESCRIPTION = "save_state_description"
    }

    private lateinit var actions: NoteActions
    private lateinit var binding: DialogAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actions = context as NoteActions
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_note, container, false)

        cancelClickListener()
        addClickListener()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_DESCRIPTION, binding.todoDescription.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_DESCRIPTION)) {
            binding.todoDescription.setText(savedInstanceState.getString(BUNDLE_DESCRIPTION))
        }
    }

    private fun cancelClickListener() {
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }
    }

    private fun addClickListener() {
        binding.btnAdd.setOnClickListener {
            actions.addNote(createTodo())
            this.dismiss()
        }
    }

    fun clear() {
        binding.todoDescription.setText("")
    }

    private fun createTodo(): Todo {
        val description = binding.todoDescription.text.toString()
        val time: Long = System.currentTimeMillis()
        return Todo(description = description, createdTime = time)
    }

    interface NoteActions {
        fun addNote(item: Todo)
    }
}