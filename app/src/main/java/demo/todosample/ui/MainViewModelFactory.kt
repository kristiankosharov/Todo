package demo.todosample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demo.todosample.repository.TodoRepository
import javax.inject.Inject

open class MainViewModelFactory @Inject constructor(
        private val todoRepository: TodoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(todoRepository) as T
    }
}