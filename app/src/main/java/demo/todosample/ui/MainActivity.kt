package demo.todosample.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import demo.bookssample.di.Injectable
import demo.todosample.R
import demo.todosample.databinding.ActivityMainBinding
import demo.todosample.entity.Todo
import demo.todosample.util.AppExecutors
import demo.todosample.util.NotesDialog
import java.util.logging.Logger
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable, NotesDialog.NoteActions {

    private val logger: Logger = Logger.getLogger("MainItem")
    private lateinit var binding: ActivityMainBinding
    private var notesDialog: NotesDialog? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders
                .of(this, mainViewModelFactory)
                .get(MainViewModel::class.java)
    }

    @Inject
    lateinit var appExecutors: AppExecutors
    private val adapter: TodoAdapter by lazy {
        TodoAdapter(appExecutors)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationIcon(android.R.drawable.ic_menu_delete)
        binding.mainToolbar.setNavigationOnClickListener {
            // TODO Handle remove click action
            Snackbar.make(binding.todoList, "Remove clicked", Snackbar.LENGTH_LONG).show()
        }

        binding.todoList.adapter = adapter
        mainViewModel.todos.observe(this, Observer {
            adapter.submitList(it)
            it.forEach { logger.info(it.toString()) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_todo -> {
                showCreateTodoDialog()
            }
        }
        return true
    }

    private fun showCreateTodoDialog() {
        NotesDialog().show(supportFragmentManager, NotesDialog.getTag())
    }

    override fun addNote(item: Todo) {
        mainViewModel.addTodo(item)
    }

}
