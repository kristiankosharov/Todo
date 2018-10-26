package demo.todosample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import demo.todosample.mock
import demo.todosample.repository.TodoRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify


@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repoViewModel: MainViewModel
    private val repository: TodoRepository = mock()

    @Before
    fun init() {
        repoViewModel = MainViewModel(repository)
    }

    @Test
    fun testNull() {
        assertThat(repoViewModel.todos, notNullValue())
        verify(repository).loadTodos()
    }

    @Test
    fun fetchTodos() {
        repoViewModel.todos.observeForever(mock())
        verify(repository).loadTodos()
    }

}