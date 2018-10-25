package demo.todosample.repository

import androidx.lifecycle.LiveData
import demo.todosample.entity.Todo
import demo.todosample.util.AppExecutors


class TodoRepository(
        private val appExecutors: AppExecutors,
        private val todoRepository: TodoRepository
) {

    fun insertTodo(item: Todo) {
        appExecutors.diskIO().execute {
            todoRepository.insertTodo(item)
        }
    }

    fun loadTodos(): LiveData<List<Todo>> {
        return todoRepository.loadTodos()
    }
}