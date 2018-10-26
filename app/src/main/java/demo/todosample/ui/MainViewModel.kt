package demo.todosample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import demo.todosample.entity.Todo
import demo.todosample.repository.TodoRepository


class MainViewModel(repository: TodoRepository) : ViewModel() {
    private var _todos: MutableLiveData<List<Todo>> = MutableLiveData()

    init {
        Transformations.map(repository.loadTodos()) {
            _todos.postValue(it)
        }
    }

    val todos: LiveData<List<Todo>>
        get() {
            return _todos
        }
}