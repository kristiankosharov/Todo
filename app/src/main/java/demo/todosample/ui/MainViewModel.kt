package demo.todosample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demo.todosample.OpenForTesting
import demo.todosample.entity.Todo
import demo.todosample.repository.TodoRepository

@OpenForTesting
class MainViewModel(private val repository: TodoRepository) : ViewModel() {
    private var _todos: LiveData<List<Todo>> = MutableLiveData()

    fun getTodos(): LiveData<List<Todo>> {
        loadTodos()
        return _todos
    }
//    val todos: LiveData<List<Todo>>
//        get() {
//            loadTodos()
//            return _todos
//        }

    private fun loadTodos() {
        _todos = repository.loadTodos()
    }

    fun addTodo(item: Todo) {
        repository.insertTodo(item)
    }

    fun updateTodo(item: Todo) {
        repository.updateTodo(item)
    }

    fun deleteAll() {
        repository.deleteAll()
    }
}