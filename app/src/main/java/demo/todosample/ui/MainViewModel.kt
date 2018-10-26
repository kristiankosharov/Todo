package demo.todosample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demo.todosample.entity.Todo
import demo.todosample.repository.TodoRepository


class MainViewModel(private val repository: TodoRepository) : ViewModel() {
//    private var _todos: LiveData<List<Todo>> = MutableLiveData()
//
//    init {
//        _todos = repository.loadTodos()
//    }

    val todos: LiveData<List<Todo>>
        get() {
            return repository.loadTodos()
        }


    fun addTodo(item: Todo) {
        repository.insertTodo(item)
    }
}