package demo.todosample

import demo.todosample.entity.Todo


object TestTodoUtil {
    fun createTodo(now: Long) = Todo(1, "Todo1", now)
}