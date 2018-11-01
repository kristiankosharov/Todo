package demo.todosample.repository

import androidx.lifecycle.LiveData
import demo.todosample.OpenForTesting
import demo.todosample.db.TodoDao
import demo.todosample.entity.Todo
import demo.todosample.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class TodoRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val todoDao: TodoDao
) {

    fun insertTodo(item: Todo) {
        appExecutors.diskIO().execute {
            todoDao.insert(item)
        }
    }

    fun loadTodos(): LiveData<List<Todo>> {
        val result: LiveData<List<Todo>> = todoDao.getAllItems()
        return result
    }

    fun updateTodo(item: Todo) {
        appExecutors.diskIO().execute {
            todoDao.update(item)
        }
    }

    fun deleteAll() {
        appExecutors.diskIO().execute {
            todoDao.deleteAll()
        }
    }
}