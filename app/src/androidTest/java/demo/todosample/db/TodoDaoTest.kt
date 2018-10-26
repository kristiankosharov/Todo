package demo.todosample.db

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import demo.todosample.LiveDataTestUtil
import demo.todosample.TestTodoUtil
import demo.todosample.entity.Todo
import demo.todosample.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
class TodoDaoTest : TodosDbTest() {
    @Test
    fun insertAndReadTodo() {
        val now = now()
        val todo = TestTodoUtil.createTodo(now)
        db.todoDao().insert(todo)
        val loaded = LiveDataTestUtil.getValue(db.todoDao().getAllItems())
        assertThat(loaded, notNullValue())
        assertThat(loaded.size, CoreMatchers.not(0))
        assertThat(loaded[0].createdTime, `is`(now))
        assertThat(loaded[0].description, `is`(equalTo("Todo1")))
        assertThat(loaded[0].id, `is`(1))
    }

    @Test
    fun insertAndObserve() {
        val observer = mock<Observer<List<Todo>>>()
        val todo = TestTodoUtil.createTodo(now())
        db.todoDao().getAllItems().observeForever(observer)
        db.todoDao().insert(todo)
        verify(observer).onChanged(Collections.singletonList(todo))
    }

    @Test
    fun deleteTodo() {
        val todoInsert = TestTodoUtil.createTodo(now())
        db.todoDao().insert(todoInsert)
        var loaded = LiveDataTestUtil.getValue(db.todoDao().getAllItems())
        val todo = loaded[0]
        db.todoDao().delete(todo)
        loaded = LiveDataTestUtil.getValue(db.todoDao().getAllItems())
        assertThat(loaded, notNullValue())
        assertThat(loaded.size, CoreMatchers.`is`(0))
    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }
}