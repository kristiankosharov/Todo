package demo.todosample.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
            }
        }
        return true
    }
}
