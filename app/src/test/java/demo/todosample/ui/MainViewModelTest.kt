package demo.todosample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import demo.todosample.LiveDataTestUtil.getValue
import demo.todosample.TestTodoUtil
import demo.todosample.entity.Todo
import demo.todosample.mock
import demo.todosample.repository.TodoRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


@RunWith(JUnit4::class)
class MainViewModelTest {


    private lateinit var repoViewModel: MainViewModel

    private val repository: TodoRepository = mock()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repoViewModel = MainViewModel(repository)
    }

    @Test
    fun fetchTodos() {
        // Create mocks for data
        val todo = TestTodoUtil.createTodo(System.currentTimeMillis())
        val dbData = MutableLiveData<List<Todo>>()
        dbData.value = listOf(todo)
        `when`(repository.loadTodos()).thenReturn(dbData)

        // Ger real data from repo
        val realData = getValue(repoViewModel.todos)

        // Check if loadTodos is called
        verify(repository).loadTodos()

        assertThat(listOf(todo), `is`(realData))
    }

    @Test
    fun addTodo() {
        // Create mocks for data
        val todo = TestTodoUtil.createTodo(System.currentTimeMillis())
        val dbData = MutableLiveData<List<Todo>>()
        dbData.value = listOf(todo)
        `when`(repository.loadTodos()).thenReturn(dbData)

        // Add todo in the repo
        repoViewModel.addTodo(todo)
        // Check if insertTodo is called
        verify(repository).insertTodo(todo)

        // Compare real data with mocked data
        val realData = getValue(repoViewModel.todos)
        assertThat(listOf(todo), `is`(realData))
    }
}