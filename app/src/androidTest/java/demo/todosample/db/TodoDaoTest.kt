package demo.todosample.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import demo.todosample.LiveDataTestUtil
import demo.todosample.entity.Todo
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TodoDaoTest : TodosDbTest() {
    @Test
    fun insertAndReadTodo() {
        val now = now()
        val todo = Todo(1, "Todo1", now)
        db.todoDao().insert(todo)
        val loaded = LiveDataTestUtil.getValue(db.todoDao().getAllItems())
        assertThat(loaded, notNullValue())
        assertThat(loaded.size, CoreMatchers.not(0))
        assertThat(loaded[0].createdTime, `is`(now))
        assertThat(loaded[0].description, `is`(equalTo("Todo1")))
        assertThat(loaded[0].id, `is`(1))
    }

    @Test
    fun deleteTodo() {
        val todoInsert = Todo(1, "Todo1", now())
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