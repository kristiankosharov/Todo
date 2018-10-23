package demo.todosample.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import demo.todosample.R
import demo.todosample.databinding.ActivityMainBinding
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private val logger: Logger = Logger.getLogger(MainActivity::class.simpleName)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationIcon(android.R.drawable.ic_menu_delete)
        binding.mainToolbar.setNavigationOnClickListener {
            // TODO Handle remove click action
            Snackbar.make(binding.todoList, "Remove clicked", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_todo -> {
                // TODO Handle add click action
                logger.info("Handle add")
                Snackbar.make(binding.todoList, "Add clicked", Snackbar.LENGTH_LONG).show()
                showCreateTodoDialog()
            }
        }
        return true
    }

    private fun showCreateTodoDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_note, binding.root as ViewGroup, false)
        val descriptionText = view.findViewById(R.id.todo_description) as EditText
        builder.setView(view)
        builder.setPositiveButton(R.string.dialog_button_add) { dialogInterface, i ->
            logger.info("Handle positive button")
            Snackbar.make(binding.todoList, "Add dialog clicked", Snackbar.LENGTH_LONG).show()

        }
        builder.setNegativeButton(android.R.string.cancel) { dialogInterface, i ->
            logger.info("Handle negative button")
            Snackbar.make(binding.todoList, "Add dialog cancel", Snackbar.LENGTH_LONG).show()
        }
        builder.show()
    }
}
