package demo.todosample.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import demo.todosample.InstantAppExecutors
import demo.todosample.TestTodoUtil
import demo.todosample.db.TodoDao
import demo.todosample.db.TodoDb
import demo.todosample.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class TodoRepositoryTest {
    private lateinit var repository: TodoRepository
    private val dao = mock<TodoDao>()
    private val db = mock<TodoDb>()
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Mockito.`when`(db.todoDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = TodoRepository(InstantAppExecutors(), dao)
    }

    @Test
    fun insertTodo() {
        val todo = TestTodoUtil.createTodo(System.currentTimeMillis())
        repository.insertTodo(todo)
        verify(dao).insert(todo)
    }

    @Test
    fun loadTodos() {
        repository.loadTodos()
        verify(dao).getAllItems()
    }
}