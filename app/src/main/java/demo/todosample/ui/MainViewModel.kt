package demo.todosample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demo.todosample.entity.Todo
import demo.todosample.repository.TodoRepository


class MainViewModel(private val repository: TodoRepository) : ViewModel() {
    private val insertTodo = MutableLiveData<Todo>()
    private var _todos: LiveData<List<Todo>> = repository.loadTodos()



    val todos: LiveData<List<Todo>>
        get() {
            return _todos
        }
}