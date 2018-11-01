package demo.todosample.utils

import androidx.lifecycle.ViewModel
import demo.todosample.repository.TodoRepository
import demo.todosample.ui.MainViewModelFactory

/**
 * Creates a one off view model factory for the given view model instance.
 */
object ViewModelUtil {
    fun <T : ViewModel> createFor(model: T, repo: TodoRepository): MainViewModelFactory {
        return object : MainViewModelFactory(repo) {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(model.javaClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return model as T
                }
                throw IllegalArgumentException("unexpected model class $modelClass")
            }
        }
    }
}